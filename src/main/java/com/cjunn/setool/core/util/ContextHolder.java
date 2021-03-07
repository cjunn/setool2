package com.cjunn.setool.core.util;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Map;

public class ContextHolder implements InitializingBean, ApplicationContextAware, ServletContextAware {
    private static final Logger LOGGER = LogManager.getLogger(ContextHolder.class);
    public static ApplicationContext appContext;
    public static ServletContext servletContext;
    public static String WEB_ROOT_DIR_PATH;
    public static File STATIC_ROOT_DIR;

    public ContextHolder() {
    }

    public static <T> T getBean(String beanName) {
        if (appContext == null) {
            LOGGER.error("applicationContext不能为空！");
            return null;
        } else if (StringUtils.isEmpty(beanName)) {
            LOGGER.error("bean名称不能为空！");
            return null;
        } else {
            Object obj = null;
            try {
                obj = appContext.getBean(beanName);
                if(obj==null){
                    LOGGER.error("找不到ID为[" + beanName + "]的spring bean实现，请检查spring配置。");
                }
            } catch (Exception ex) {
                LOGGER.error("获取spring bean[" + beanName + "]错误：" + ex.getMessage(), ex);
                return null;
            }
            return (T)obj;
        }
    }

    public static final <T> T getBean(Class<T> requireType) {
        return appContext.getBean(requireType);
    }

    public static final <T> Map<String, T> getBeans(Class<T> requireType) {
        return appContext.getBeansOfType(requireType);
    }

    public static final <T> Optional<T> getOptionalBean(String beanName) {
        try {
            return Optional.of(getBean(beanName));
        } catch (BeansException ex) {
            LOGGER.error("获取spring bean[" + beanName + "]出错", ex);
            return Optional.absent();
        }
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (appContext != null) {
            throw new BeanCreationException("applicationContext已经被初始化");
        } else {
            appContext = context;
            LOGGER.info("holded applicationContext,displayName:" + appContext.getDisplayName());
        }
    }

    public void setServletContext(ServletContext context) {
        servletContext = context;
    }

    public void afterPropertiesSet() throws Exception {
        ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
        if (servletContext != null) {
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            LOGGER.info("获取的静态默认路径WEB_ROOT_DIR_PATH：" + path);
            WEB_ROOT_DIR_PATH = this.calDirPath(path);
            LOGGER.info("处理后静态默认路径WEB_ROOT_DIR_PATH：" + WEB_ROOT_DIR_PATH);
        }

    }

    public static final String resolveEmbeddedValue(String propName) {
        return appContext == null ? null : ((AbstractApplicationContext)appContext).getBeanFactory().resolveEmbeddedValue(propName);
    }

    private String calDirPath(String path) {
        boolean equals = "\\".equals(System.getProperty("file.separator"));
        String spl = equals ? "\\\\" : "\\/";
        if (StringUtils.startsWith(path, "file")) {
            path = path.substring(5);
            if (equals) {
                path = path.substring(1);
            }
            int max = Math.max(path.indexOf(".jar!"), path.indexOf(".war!"));
            path = path.substring(0, max);
            path = path.substring(0, Math.max(path.lastIndexOf("/"), path.lastIndexOf("\\")) + 1);
        } else if (equals && StringUtils.startsWith(path, "/")) {
            path = path.substring(1);
        }
        path = path.replaceAll("\\\\", spl).replaceAll("\\/", spl);
        return path;
    }
}
