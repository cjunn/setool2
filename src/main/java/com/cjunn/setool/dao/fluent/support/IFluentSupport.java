package com.cjunn.setool.dao.fluent.support;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.fluent.builder.frame.TableFrame;
import com.cjunn.setool.test.TestMode;

import java.util.List;

public interface IFluentSupport {
    <T extends BaseModel> TableFrame<T> table(Class<T> clz);

    <T extends BaseModel> List<T> selectCmd(TableFrame<T> tableFrame);
    <T extends BaseModel> T selectOneCmd(TableFrame<T> tableFrame);
    <T extends BaseModel> Integer countCmd(TableFrame<T> tableFrame);

    <T extends BaseModel> Integer updateModel(TableFrame<T> tableFrame);
    <T extends BaseModel> Integer updateWhere(TableFrame<T> tableFrame);
    <T extends BaseModel> Integer deleteByPk(TableFrame<T> tableFrame);
    <T extends BaseModel> Integer deleteWhere(TableFrame<T> tableFrame);
}
