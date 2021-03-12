package com.cjunn.setool.dao.fluent.support;

import com.cjunn.setool.dao.fluent.builder.frame.TableFrame;

public interface IFluentSupport {
    public void execute(TableFrame tableFrame);
    TableFrame table(Class<?> clz);
}
