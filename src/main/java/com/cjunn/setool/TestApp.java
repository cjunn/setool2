package com.cjunn.setool;

import com.alibaba.fastjson.JSON;
import com.cjunn.setool.aop.EnableAopMethod;
import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.monitor.MonitorContextHolder;
import com.cjunn.setool.dao.fluent.builder.frame.AndOrFrame;
import com.cjunn.setool.dao.fluent.builder.frame.SelectFrame;
import com.cjunn.setool.dao.fluent.builder.frame.TableFrame;
import com.cjunn.setool.dao.fluent.builder.frame.WhereFrame;
import com.cjunn.setool.dao.fluent.support.mybatis.MybsFluentSupport;
import com.cjunn.setool.dao.fluent.support.mybatis.MybsMapper;
import com.cjunn.setool.test.TestMode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@MapperScan("com.cjunn")
@EnableAopMethod
@SpringBootApplication
public class TestApp {


  public static void main(String[] args) throws InterruptedException {
      ConfigurableApplicationContext run = SpringApplication.run(TestApp.class, args);
      MybsFluentSupport bean = run.getBean(MybsFluentSupport.class);

      TableFrame<TestMode> table = bean.table(TestMode.class);
      TestMode testMode = table.where()
              .and()
              .andLike("ttt", "%b%")
              .or()
              .andEq("a", "c")
              .andEq("bc", "e")
              .selectOneCmd();
      System.err.println(JSON.toJSONString(testMode));


  }

}
