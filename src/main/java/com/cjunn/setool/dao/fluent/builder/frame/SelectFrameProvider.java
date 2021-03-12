package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;

/**
 * @ClassName SelectProvider
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:00
 * @Version
 */
public interface SelectFrameProvider<T extends BaseModel> extends IOptFrame<TableFrame> {
    default SelectFrame<T> select(){
        return this.topFrame().select();
    }
}
