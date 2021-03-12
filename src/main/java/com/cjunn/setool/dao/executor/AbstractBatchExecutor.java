package com.cjunn.setool.dao.executor;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.HandlerAnnoModelInfo;
import com.cjunn.setool.core.model.HandlerType;
import com.cjunn.setool.core.model.JpaAnnoModeInfo;
import com.google.common.collect.Iterables;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Iterator;

public abstract class AbstractBatchExecutor<T extends BaseModel> implements BatchExecutor<T> {
    private final static Log log = LogFactory.getLog(AbstractBatchExecutor.class);
    protected JdbcTemplate jdbcTemplate;
    protected Class<T> modelClz;
    protected JpaAnnoModeInfo modelInfo;


    protected <T extends BaseModel> void doFieldHandler(Iterable<T> source, HandlerType handlerType) {
        HandlerAnnoModelInfo.doFieldHandler(source,handlerType);
    }


    public Class<T> getModelClz() {
        return this.modelClz;
    }
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    public void setModelClz(Class<T> modelClz) {
        this.modelClz = modelClz;
        this.modelInfo = JpaAnnoModeInfo.of(modelClz);
    }
    public JpaAnnoModeInfo getModelInfo() {
        return modelInfo;
    }




}
