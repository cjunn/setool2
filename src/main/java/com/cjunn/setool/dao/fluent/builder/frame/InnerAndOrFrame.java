package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.fluent.builder.BiExps;

/**
 * @ClassName InnerAndOrFrame
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 22:23
 * @Version
 */
public class InnerAndOrFrame<T extends BaseModel> extends AndOrFrame<T> {
    public InnerAndOrFrame(BiExps leftBiExps, TableFrame tableFrame) {
        super(leftBiExps, null, tableFrame);
    }

    @Override
    public AndOrFrame or() {
        throw new IllegalStateException("warn");
    }

    @Override
    public AndOrFrame and() {
        throw new IllegalStateException("warn");
    }
}
