package com.cjunn.setool.dao.fluent.support;

import com.cjunn.setool.dao.fluent.builder.frame.TableFrame;

/**
 * @ClassName AbstractFluentSupport
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 22:03
 * @Version
 */
public abstract class AbstractFluentSupport implements IFluentSupport {
    @Override
    public TableFrame table(Class<?> clz) {
        return new TableFrame(clz,this);
    }
}
