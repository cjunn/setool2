package com.cjunn.setool.test;

import com.cjunn.setool.aop.handler.TakeTimeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@TakeTimeLog
@Component
public class TestService implements ITestService {

    @Autowired
    TestMapper testMapper;

    @Override
    public void test() {
        System.err.println(testMapper.updateOne(new TestMode()));
    }
}
