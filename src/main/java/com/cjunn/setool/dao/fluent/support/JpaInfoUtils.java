package com.cjunn.setool.dao.fluent.support;

import com.cjunn.setool.utils.Pair;
import com.cjunn.setool.utils.Predicates2;
import com.google.common.base.CaseFormat;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName JpaUtils
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/12 16:51
 * @Version
 */
public class JpaInfoUtils {
    public static void of(Class<?> clz, JpaFiledConsumer consumer){
        Iterator<Field> iterator = loadColumnAnnotatedFields(clz).iterator();
        while(iterator.hasNext()){
            Field next = iterator.next();
            consumerField(next,consumer);
        }
    }
    private static void consumerField(Field field,JpaFiledConsumer consumer) {
        String fieldName = field.getName();
        Class<?> type = field.getType();
        String columnName;
        if (field.isAnnotationPresent(Column.class)) {
             columnName = ((Column)field.getAnnotation(Column.class)).name();
        } else {
            columnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, fieldName);
        }
        consumer.accept(type,fieldName,columnName);
    }

    private static Iterable<Field> loadColumnAnnotatedFields(Class<?> modelClazz) {
        final ArrayList<Method> methods = Lists.newArrayList(modelClazz.getMethods());
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(modelClazz);
        return ImmutableList.copyOf(Iterables.filter(allFieldsList, Predicates.or(Predicates.and( Predicates2.isAnnotationPresent(Column.class)), Predicates.compose(new Predicate<String>() {
            public boolean apply( String getterName) {
                return Iterables.any(methods, Predicates.and(Predicates.and( Predicates2.isAnnotationPresent(Column.class)), Predicates.compose(Predicates.equalTo(getterName), new Function<Method, String>() {
                    public String apply(Method modelMethod) {
                        return modelMethod.getName();
                    }
                })));
            }
        }, (Field columnUnAnnotatedField)->"get" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, columnUnAnnotatedField.getName())))));
    }

    @FunctionalInterface
    public interface JpaFiledConsumer {
        void accept(Class<?> fieldClz,String propertyName,String columnName);
    }
}
