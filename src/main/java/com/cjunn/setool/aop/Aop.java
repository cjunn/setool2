package com.cjunn.setool.aop;

import com.cjunn.setool.aop.handler.EmptyMethodHandler;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface Aop {
    Class<? extends MethodInterceptor> handler() default EmptyMethodHandler.class;
    int order() default 0;
}
