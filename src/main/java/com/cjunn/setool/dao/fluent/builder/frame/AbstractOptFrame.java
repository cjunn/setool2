package com.cjunn.setool.dao.fluent.builder.frame;

/**
 * @ClassName AbstractOptFrame
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:12
 * @Version
 */
public abstract class AbstractOptFrame<T extends IOptFrame> implements IOptFrame<T> {
    T topFrame;
    public AbstractOptFrame(T topFrame){
        this.topFrame=topFrame;
    }
    @Override
    public T topFrame() {
        return topFrame;
    }
    @Override
    public abstract String exportQL();
}
