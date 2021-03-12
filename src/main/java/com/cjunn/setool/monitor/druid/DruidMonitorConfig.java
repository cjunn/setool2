package com.cjunn.setool.monitor.druid;

import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.cjunn.setool.core.monitor.UserMonitor;
import com.cjunn.setool.core.monitor.sql.SQLInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @ClassName DruidMonitorConfig
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 15:01
 * @Version
 */
@Configuration
public class DruidMonitorConfig {
    @ConditionalOnBean(DruidDataSource.class)
    @ConditionalOnProperty(name="setool.monitor.druid.usersql",havingValue = "true")
    @Bean
    public UserMonitor<SQLInfo> druidUserMonitor(DruidDataSource druidDataSource){
        Stream stream;
        ExecuteSqlMonitorFilter filter = new ExecuteSqlMonitorFilter();
        druidDataSource.setProxyFilters(Arrays.asList(filter));
        return filter.getUserMonitor();
    }
}
