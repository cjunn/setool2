package com.cjunn.setool.dao.fluent.builder.frame;

/**
 * @ClassName UpdateFrameProvider
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:40
 * @Version
 */
public interface UpdateFrameProvider  extends IOptFrame<TableFrame>  {
    default UpdateFrame update(){
        return this.topFrame().update();
    }
}
