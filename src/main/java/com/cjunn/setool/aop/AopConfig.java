package com.cjunn.setool.aop;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Role;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class AopConfig {
    public AopConfig(){
    }



    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    AopAdvisor logAdvisor(){
        return new AopAdvisor();
    }
}
