package com.cjunn.setool.aop.handler;

import com.cjunn.setool.dao.IdMaker;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TakeTimeLogHandler implements MethodInterceptor {
    public static final Logger LOGGER = LoggerFactory.getLogger(TakeTimeLogHandler.class);
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start=System.currentTimeMillis();
        String startMsg = "[" + invocation.getMethod().toString() + " start]";
        LOGGER.debug(startMsg);
        System.err.println(startMsg);
        Object proceed = invocation.proceed();
        long end=System.currentTimeMillis();
        String endMsg = "[" + invocation.getMethod().toString() + "   end]:[" + (end - start) + "ms]";
        LOGGER.debug(endMsg);
        System.err.println(endMsg);
        return proceed;
    }
}
