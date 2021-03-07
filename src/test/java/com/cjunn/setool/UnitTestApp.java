package com.cjunn.setool;


import com.cjunn.setool.test.ITestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class UnitTestApp {
    @Autowired
        ITestService iTestService;

    @Test
    public void test() throws InterruptedException {
        iTestService.test();
        Thread.sleep(100000l);
    }
}
