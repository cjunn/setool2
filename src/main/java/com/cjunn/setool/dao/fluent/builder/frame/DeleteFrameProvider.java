package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;

/**
 * @ClassName UpdateFrameProvider
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:40
 * @Version
 */
public interface DeleteFrameProvider<T extends BaseModel> extends IOptFrame<TableFrame>  {
    default DeleteFrame<T> delete(){
        return this.topFrame().delete();
    }
}
