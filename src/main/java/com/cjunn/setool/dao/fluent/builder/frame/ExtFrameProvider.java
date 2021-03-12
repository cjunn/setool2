package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;

/**
 * @ClassName ExtFrameProvider
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 15:01
 * @Version
 */
public interface ExtFrameProvider<T extends BaseModel> extends IOptFrame<TableFrame> {
    default ExtFrame<T> ext(){
        return this.topFrame().ext();
    }
}
