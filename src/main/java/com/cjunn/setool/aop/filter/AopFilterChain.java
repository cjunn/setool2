package com.cjunn.setool.aop.filter;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AopFilterChain implements MethodInterceptor {
    private int pos;
    private final List<AopFilter> aopFilters;
    private final int filterSize;
    public AopFilterChain(List<AopFilter> aopFilters ,int pos){
        this.aopFilters=aopFilters;
        this.filterSize=aopFilters.size();
    }
    public AopFilter nextFilter(){
        AopFilter filter = aopFilters.get(this.pos++);
        return filter;
    }
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        NextMethodInvocation nextMethodInvocation = invocation instanceof NextMethodInvocation ? (NextMethodInvocation)invocation : new NextMethodInvocation(invocation);
        return this.pos < this.filterSize ?
                this.nextFilter().invoke(nextMethodInvocation) : nextMethodInvocation.methodInvocation.proceed();
    }

    public class NextMethodInvocation implements MethodInvocation{
        private MethodInvocation methodInvocation;
        private Map<String,Object> transferMap=new HashMap<>();
        public Map<String, Object> getTransferMap() {
            return transferMap;
        }
        public NextMethodInvocation(MethodInvocation methodInvocation){
            this.methodInvocation=methodInvocation;
        }
        @Override
        public Object proceed() throws Throwable {
            return AopFilterChain.this.invoke(this);
        }
        @Override
        public Method getMethod() {
            return methodInvocation.getMethod();
        }
        @Override
        public Object[] getArguments() {
            return methodInvocation.getArguments();
        }
        @Override
        public Object getThis() {
            return methodInvocation.getThis();
        }
        @Override
        public AccessibleObject getStaticPart() {
            return methodInvocation.getStaticPart();
        }
    }

}
