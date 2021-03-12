package com.cjunn.setool.dao.fluent.builder.frame;

/**
 * @ClassName UpdateFrameProvider
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:40
 * @Version
 */
public interface DeleteFrameProvider extends IOptFrame<TableFrame>  {
    default DeleteFrame delete(){
        return this.topFrame().delete();
    }
}
