package com.cjunn.setool.aop.handler;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TakeTimeLogHandler implements MethodInterceptor {
    protected Log log = LogFactory.getLog(this.getClass());
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start=System.currentTimeMillis();
        String startMsg = "[" + invocation.getMethod().toString() + " start]";
        log.debug(startMsg);
        System.err.println(startMsg);
        Object proceed = invocation.proceed();
        long end=System.currentTimeMillis();
        String endMsg = "[" + invocation.getMethod().toString() + "   end]:[" + (end - start) + "ms]";
        log.debug(endMsg);
        System.err.println(endMsg);
        return proceed;
    }
}
