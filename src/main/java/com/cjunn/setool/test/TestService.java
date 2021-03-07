package com.cjunn.setool.test;

import com.cjunn.setool.aop.Aop;
import com.cjunn.setool.aop.handler.TakeTimeLog;
import com.cjunn.setool.aop.handler.TakeTimeLogHandler;
import org.springframework.stereotype.Component;
@TakeTimeLog
@Component
public class TestService implements ITestService {


    @Override
    public void test() {
        System.err.println("test");
    }
}
