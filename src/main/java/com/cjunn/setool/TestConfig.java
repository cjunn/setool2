package com.cjunn.setool;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName TestConfig
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 18:08
 * @Version
 */
@Configuration
public class TestConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }
}
