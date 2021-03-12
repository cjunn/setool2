package com.cjunn.setool.dao.fluent.support;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.fluent.builder.frame.TableFrame;
import com.cjunn.setool.test.TestMode;

/**
 * @ClassName AbstractFluentSupport
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 22:03
 * @Version
 */
public abstract class AbstractFluentSupport implements IFluentSupport {
    @Override
    public <T extends BaseModel> TableFrame<T> table(Class<T> clz) {
        return new TableFrame(clz,this);
    }
}
