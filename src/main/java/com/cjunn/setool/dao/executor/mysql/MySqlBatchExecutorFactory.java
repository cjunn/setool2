package com.cjunn.setool.dao.executor.mysql;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.executor.AbstractBatchExecutorFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public final class MySqlBatchExecutorFactory extends AbstractBatchExecutorFactory {
    private String jdbcTemplateName;

    public MySqlBatchExecutorFactory() {
    }

    @Override
    public <T extends BaseModel> BeanDefinition getBatchExecutorBeanDefinition(Class<T> modelClass) {
        return BeanDefinitionBuilder.genericBeanDefinition(MySqlBatchExecutor.class)
                .addPropertyValue("modelClz", modelClass)
                .addPropertyReference("jdbcTemplate", this.jdbcTemplateName)
                .getBeanDefinition();
    }



    public void setJdbcTemplateName(String jdbcTemplateName) {
        this.jdbcTemplateName = jdbcTemplateName;
    }
}