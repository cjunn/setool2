package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;

/**
 * @ClassName WhereFrameProvider
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:23
 * @Version
 */
public interface WhereFrameProvider<T extends BaseModel> extends IOptFrame<TableFrame> {
    default WhereFrame<T> where(){
        return this.topFrame().where();
    }
}
