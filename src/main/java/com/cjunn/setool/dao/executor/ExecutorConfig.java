package com.cjunn.setool.dao.executor;

import com.cjunn.setool.dao.executor.mysql.MySqlBatchExecutorFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ExecutorConfig {
    private static final String JDBC_NAME="setool.dao.executor.jdbcTemplateName";
    private static final String SCAN_PATH="setool.dao.executor.scanPath";
    @ConditionalOnProperty(name="setool.dao.executor.type",havingValue = "mysql",matchIfMissing=true)
    @Bean
    AbstractBatchExecutorFactory mysqlBatchExecutorFactory(Environment environment){
        String jdbcTemplateName = environment.getProperty(JDBC_NAME);
        String scanPath = environment.getProperty(SCAN_PATH);
        MySqlBatchExecutorFactory mySqlBatchExecutorFactory = new MySqlBatchExecutorFactory();
        mySqlBatchExecutorFactory.setJdbcTemplateName(jdbcTemplateName==null?"jdbcTemplate":jdbcTemplateName);
        mySqlBatchExecutorFactory.setBatchExecutorInjectScanPath(scanPath==null?"":scanPath);
        return mySqlBatchExecutorFactory;
    }






}
