package com.cjunn.setool;

import com.cjunn.setool.aop.Aop;
import com.cjunn.setool.aop.EnableAopMethod;
import com.cjunn.setool.dao.executor.AbstractBatchExecutor;
import com.cjunn.setool.dao.executor.AbstractBatchExecutorFactory;
import com.cjunn.setool.dao.executor.BatchExecutor;
import com.cjunn.setool.dao.executor.ExecutorConfig;
import com.cjunn.setool.test.ITestService;
import com.cjunn.setool.test.TestMode;
import com.cjunn.setool.test.TestService;
import com.cjunn.setool.utils.AnnoUtils;
import com.google.common.base.CaseFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.SpringCglibInfo;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableAopMethod
@SpringBootApplication
public class TestApp {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(TestApp.class, args);

      AbstractBatchExecutorFactory executorFactory = run.getBean(AbstractBatchExecutorFactory.class);
        BatchExecutor<TestMode> batchExecutor = executorFactory.getBatchExecutor(TestMode.class);
        List<TestMode> ite = new ArrayList<>();
        ite.add(new TestMode());
        ite.add(new TestMode());
        batchExecutor.save(ite);
        batchExecutor.update(ite);
        System.err.println("321");
    }

}
