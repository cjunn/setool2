package com.cjunn.setool.dao.mybatis;

import com.cjunn.setool.dao.mybatis.intercept.OptimisticLockInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisConfig
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 20:55
 * @Version
 */
@Configuration
@ConditionalOnClass({MybatisAutoConfiguration.class})
public class MybatisInterceptConfig {
    @Bean
    ConfigurationCustomizer featuresIntercept(){
        return configuration -> configuration.addInterceptor(new OptimisticLockInterceptor());

    }
}
