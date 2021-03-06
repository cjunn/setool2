package com.cjunn.setool.aop;

import com.cjunn.setool.utils.AnnoUtils;
import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class AopAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    public AopAdvisor(){
        this.setAdvice(new AopAdvice());
    }

    @Override
    public Advice getAdvice() {
        return super.getAdvice();
    }

    @Override
    public Pointcut getPointcut() {
        return new StaticMethodMatcherPointcut(){
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return
                        AnnoUtils.lookupAnnoFromClz(targetClass,Aop.class).size() > 0 ||
                        AnnoUtils.lookupAnnoFromMethod(method,Aop.class).size() > 0 ;
            }

            @Override
            public ClassFilter getClassFilter() {
                return super.getClassFilter();
            }
        };
    }
}
