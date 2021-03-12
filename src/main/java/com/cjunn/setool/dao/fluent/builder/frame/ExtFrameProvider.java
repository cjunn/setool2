package com.cjunn.setool.dao.fluent.builder.frame;

/**
 * @ClassName ExtFrameProvider
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 15:01
 * @Version
 */
public interface ExtFrameProvider extends IOptFrame<TableFrame> {
    default ExtFrame ext(){
        return this.topFrame().ext();
    }
}
