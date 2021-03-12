package com.cjunn.setool.aop;

import com.cjunn.setool.aop.filter.AopFilter;
import com.cjunn.setool.aop.filter.AopFilterChain;
import com.cjunn.setool.dao.IdMaker;
import com.cjunn.setool.utils.AnnoUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class AopAdvice implements MethodInterceptor {
    public static final Logger LOGGER = LoggerFactory.getLogger(AopAdvice.class);
    private static final Map<Object,List<AopFilter>> AOP_FILTERS_CACHE = new ConcurrentHashMap<>();



    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        AopFilterChain methodChain = obj2AopFile(invocation.getMethod());
        if(methodChain!=null){
            return methodChain.invoke(invocation);
        }
        AopFilterChain clzChain = obj2AopFile(invocation.getThis().getClass());
        if(clzChain!=null){
            return clzChain.invoke(invocation);
        }
        return invocation.proceed();

    }

    public AopFilterChain obj2AopFile(Object obj){
        List<AopFilter> aopFilters = AOP_FILTERS_CACHE.computeIfAbsent(obj,(o)->{
            List<? extends Annotation> annotations =
                    (o instanceof Method) ?
                            AnnoUtils.lookupAnnoFromMethod((Method) o, Aop.class) :
                            AnnoUtils.lookupAnnoFromClz((Class) o, Aop.class);
           return  aops2AopFilter((List<Aop>)annotations);
        });
        if(aopFilters!=null&&aopFilters.size()>0){
            return aopFilter2AopChain(aopFilters);
        }
        return null;
    }

    private AopFilterChain aopFilter2AopChain(List<AopFilter> aopFilters){
        return new AopFilterChain(aopFilters, 0);
    }
    private List<AopFilter> aops2AopFilter(List<Aop> aops ){
        List<AopFilter> aopFilters=new ArrayList<>();
        try {
            Collections.sort(aops, Comparator.comparingInt(Aop::order));
            for(Aop aop:aops){
                aopFilters.add(new AopFilter(aop.handler().newInstance()));
            }
        } catch (Exception e) {
            LOGGER.error("aops2AopFilter",e);
            throw new IllegalStateException(e);
        }
        return aopFilters;
    }


}
