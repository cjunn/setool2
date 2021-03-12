package com.cjunn.setool;

import com.cjunn.setool.aop.EnableAopMethod;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("com.cjunn")
@EnableAopMethod
@SpringBootApplication
public class TestApp {


  public static void main(String[] args) throws InterruptedException {
      ConfigurableApplicationContext run = SpringApplication.run(TestApp.class, args);


  }


}
