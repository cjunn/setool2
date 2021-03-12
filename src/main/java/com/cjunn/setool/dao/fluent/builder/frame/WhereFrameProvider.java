package com.cjunn.setool.dao.fluent.builder.frame;

/**
 * @ClassName WhereFrameProvider
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:23
 * @Version
 */
public interface WhereFrameProvider extends IOptFrame<TableFrame> {
    default WhereFrame where(){
        return this.topFrame().where();
    }
}
