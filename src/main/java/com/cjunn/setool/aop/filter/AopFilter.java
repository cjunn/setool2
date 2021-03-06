package com.cjunn.setool.aop.filter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AopFilter implements MethodInterceptor {
    private MethodInterceptor methodInterceptor;
    public AopFilter(MethodInterceptor methodInterceptor){
        this.methodInterceptor=methodInterceptor;
    }
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return methodInterceptor.invoke(invocation);
    }
}
