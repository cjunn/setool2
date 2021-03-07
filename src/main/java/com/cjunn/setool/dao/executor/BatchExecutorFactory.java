package com.cjunn.setool.dao.executor;

import com.cjunn.setool.core.model.BaseModel;
import org.springframework.beans.factory.config.BeanDefinition;

public interface BatchExecutorFactory {
    <T extends BaseModel> BeanDefinition getBatchExecutorBeanDefinition(Class<T> clz);

    <T extends BaseModel> BatchExecutor<T> getBatchExecutor(Class<T> clz);
}
