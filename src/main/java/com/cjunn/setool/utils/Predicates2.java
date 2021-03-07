package com.cjunn.setool.utils;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.apache.commons.lang3.Validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public final class Predicates2 {
    public Predicates2() {
    }

    public static <T extends AnnotatedElement> Predicate<T> isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return new Predicates2.AnnotationPresent(annotationClass);
    }

    public static <T> Predicate<T> propertyPredicateEqualTo(String property, Object value) {
        return propertyPredicate(property, Predicates.equalTo(value));
    }

    public static <T, V> Predicate<T> propertyPredicate(String property, Predicate<V> propertyPredicate) {
        Validate.notBlank(property, "创建Bean 属性谓词时， 属性名`property`不能为空", new Object[0]);
        Validate.notNull(property, "创建Bean 属性谓词时， 属性谓词`propertyPredicate`不能为空", new Object[0]);
        return new Predicates2.PropertyPredicate(property, propertyPredicate);
    }

    private static class AnnotationPresent<T extends AnnotatedElement> implements Predicate<T> {
        private Class<? extends Annotation> annotationClass;

        AnnotationPresent(Class<? extends Annotation> annotationClass) {
            this.annotationClass = annotationClass;
        }

        public boolean apply(T input) {
            return input != null && input.isAnnotationPresent(this.annotationClass);
        }
    }

    static class PropertyPredicate<T, V> implements Predicate<T> {
        private String property;
        private Predicate<V> propertyPredicate;

        PropertyPredicate(String property, Predicate<V> propertyPredicate) {
            this.property = property;
            this.propertyPredicate = propertyPredicate;
        }

        public boolean apply(T input) {
            return input != null && this.propertyPredicate.apply((V)Reflections.getFieldValue(input, this.property));
        }
    }
}