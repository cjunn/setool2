package com.cjunn.setool.dao.fluent.builder.frame;

/**
 * @ClassName SelectProvider
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:00
 * @Version
 */
public interface SelectFrameProvider extends IOptFrame<TableFrame> {
    default SelectFrame select(){
        return this.topFrame().select();
    }
}
