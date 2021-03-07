package com.cjunn.setool.dao.executor;

import com.cjunn.setool.core.model.BaseModel;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.reflect.ClassPath;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.Ordered;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractBatchExecutorFactory implements BatchExecutorFactory, BeanFactoryAware, ApplicationContextAware, BeanDefinitionRegistryPostProcessor, InitializingBean, ResourceLoaderAware, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(AbstractBatchExecutorFactory.class);
    private String batchExecutorInjectScanPath="";
    private ResourceLoader resourceLoader;
    private ImmutableSet<AbstractBatchExecutorFactory.FieldInjectBatchExecutorInfo> requireInjectFieldInfos;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;



    public AbstractBatchExecutorFactory() {
    }

    public <T extends BaseModel> BatchExecutor<T> getBatchExecutor(final Class<T> modelClass) {
        Map<String, AbstractBatchExecutor> batchExecutorMap = this.applicationContext.getBeansOfType(AbstractBatchExecutor.class);
        Optional<AbstractBatchExecutor> batchExecutorOptional = Iterables.tryFind(
                batchExecutorMap.values(),
                batchExecutor -> batchExecutor != null && TypeUtils.isAssignable(batchExecutor.getModelClz(), modelClass)
        );
        if (!batchExecutorOptional.isPresent()) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry)this.beanFactory;
            BeanDefinition batchExecutorBeanDefinition = this.getBatchExecutorBeanDefinition(modelClass);
            String batchExecutorName = this.suffixBatchExecutorName(modelClass);
            registry.registerBeanDefinition(batchExecutorName, batchExecutorBeanDefinition);
            return (BatchExecutor)this.applicationContext.getBean(batchExecutorName);
        } else {
            return (BatchExecutor)batchExecutorOptional.get();
        }
    }

    @Override
    public int getOrder() {
        return 2147483647;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        preRegistScanPathModeClz(registry);
    }
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
    private void preInitScanPathModeClz() throws Exception{
        ImmutableSet.Builder<FieldInjectBatchExecutorInfo> requireInjectFieldInfosBuilder = ImmutableSet.builder();
        ClassPath classPath = ClassPath.from(this.resourceLoader.getClassLoader());
        ImmutableSet<ClassPath.ClassInfo> classInfos = classPath.getTopLevelClassesRecursive(this.batchExecutorInjectScanPath);
        Iterable<Class<?>> classes = Iterables.transform( classInfos,
                input -> input.load());
        Iterator<Class<?>> iterator = classes.iterator();
        while(iterator.hasNext()) {
            Class<?> beanClass = iterator.next();
            List<Field> fieldsList = FieldUtils.getAllFieldsList(beanClass);
            Iterator<Field> FieldIterator = fieldsList.iterator();
            while(FieldIterator.hasNext()) {
                Field field = FieldIterator.next();
                Optional<AbstractBatchExecutorFactory.FieldInjectBatchExecutorInfo> option = AbstractBatchExecutorFactory.FieldInjectBatchExecutorInfo.from(field);
                if (option.isPresent()) {
                    requireInjectFieldInfosBuilder.add(option.get());
                }
            }
        }

        this.requireInjectFieldInfos = requireInjectFieldInfosBuilder.build();
    }
    private void preRegistScanPathModeClz(BeanDefinitionRegistry registry){
        UnmodifiableIterator<FieldInjectBatchExecutorInfo> iterator = this.requireInjectFieldInfos.iterator();
        while(iterator.hasNext()) {
            AbstractBatchExecutorFactory.FieldInjectBatchExecutorInfo info = iterator.next();
            if (!registry.containsBeanDefinition(info.getRequireInjectBeanName())) {
                BeanDefinition definition = this.getBatchExecutorBeanDefinition(info.getModelClass());
                registry.registerBeanDefinition(info.getRequireInjectBeanName(), definition);
            }
        }
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        preInitScanPathModeClz();
    }
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    @Override
    public abstract <T extends BaseModel> BeanDefinition getBatchExecutorBeanDefinition(Class<T> clz);

    private String suffixBatchExecutorName(Class<?> modelClass) {
        String modelName = ClassUtils.getSimpleName(modelClass);
        String batchExecutorName = modelName + "BatchExecutor";
        if (!this.applicationContext.containsBean(batchExecutorName)) {
            return batchExecutorName;
        } else {
            int i;
            for(i = 1; this.applicationContext.containsBean(batchExecutorName + i); ++i) {}
            return batchExecutorName + i;
        }
    }

    public void setBatchExecutorInjectScanPath(String batchExecutorInjectScanPath) {
        this.batchExecutorInjectScanPath = batchExecutorInjectScanPath;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    private static class FieldInjectBatchExecutorInfo {
        private Field field;
        private String requireInjectBeanName;
        private Class<? extends BaseModel> modelClass;

        public FieldInjectBatchExecutorInfo(Field field) {
            this.field = field;
        }

        public static Optional<FieldInjectBatchExecutorInfo> from(Field field) {
            if (TypeUtils.isAssignable(field.getGenericType(), BatchExecutor.class)) {
                AbstractBatchExecutorFactory.FieldInjectBatchExecutorInfo fieldInjectBatchExecutorInfo = new AbstractBatchExecutorFactory.FieldInjectBatchExecutorInfo(field);
                Optional<String> requireBeanNameFromField = fieldInjectBatchExecutorInfo.loadRequireBeanNameFromField();
                if (requireBeanNameFromField.isPresent()) {
                    fieldInjectBatchExecutorInfo.requireInjectBeanName = requireBeanNameFromField.get();
                    Optional modelClass = getModelClassFromFieldParameterizedType(field);
                    if (modelClass.isPresent()) {
                        fieldInjectBatchExecutorInfo.modelClass = (Class)modelClass.get();
                        return Optional.of(fieldInjectBatchExecutorInfo);
                    } else {
                        AbstractBatchExecutorFactory.logger.error("{} 类的BatchExecutor : {}， 自动创建Bean失败， 原因： `BatchExecutor必须带有BaseModel的协变泛型信息。 ", field.getGenericType(), field.getName());
                        return Optional.absent();
                    }
                } else {
                    AbstractBatchExecutorFactory.logger.error("{} 类的BatchExecutor : {}， 自动创建Bean失败， 原因： `BatchExecutor` 只支持 `spring @Autowired` 或 `javax.annotation.Resource` 自动注入。 ", field.getGenericType(), field.getName());
                    return Optional.absent();
                }
            } else {
                return Optional.absent();
            }
        }

        private static Optional<? extends Class<? extends BaseModel>> getModelClassFromFieldParameterizedType(Field field) {
            Type fieldGenericType = field.getGenericType();
            if (TypeUtils.isInstance(fieldGenericType, ParameterizedType.class)) {
                ParameterizedType fieldGenericParameterizedType = (ParameterizedType)fieldGenericType;
                Type[] actualTypeArguments = fieldGenericParameterizedType.getActualTypeArguments();
                try {
                    Object of = Optional.of((Class) actualTypeArguments[0]);
                    return (Optional<? extends Class<? extends BaseModel>>) of;
                } catch (Exception ex) {
                    logger.error("getModelClassFromFieldParameterizedType",ex);
                }
            }

            return Optional.absent();
        }

        private Optional<String> loadRequireBeanNameFromField() {
            return this.loadRequireBeanNameIfAutowiredPresent().or(this.loadRequireBeanNameIfResourcePresent());
        }

        private Optional<String> loadRequireBeanNameIfAutowiredPresent() {
            if (this.field.isAnnotationPresent(Autowired.class)) {
                if (this.field.isAnnotationPresent(Qualifier.class)) {
                    String qualifierName = (this.field.getAnnotation(Qualifier.class)).value();
                    if (StringUtils.isNoneBlank(new CharSequence[]{qualifierName})) {
                        return Optional.of(qualifierName);
                    }
                }
                return Optional.of(this.field.getName());
            } else {
                return Optional.absent();
            }
        }

        private Optional<String> loadRequireBeanNameIfResourcePresent() {
            if (this.field.isAnnotationPresent(Resource.class)) {
                String resourceBeanName = this.field.getAnnotation(Resource.class).name();
                return StringUtils.isNoneBlank(new CharSequence[]{resourceBeanName}) ? Optional.of(resourceBeanName) : Optional.of(this.field.getName());
            } else {
                return Optional.absent();
            }
        }

        public Field getField() {
            return this.field;
        }

        public String getRequireInjectBeanName() {
            return this.requireInjectBeanName;
        }

        public Class<? extends BaseModel> getModelClass() {
            return this.modelClass;
        }
    }
}
