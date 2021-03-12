package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;

/**
 * @ClassName UpdateFrame
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:37
 * @Version
 */
public class UpdateFrame<T extends BaseModel> extends AbstractOptFrame<TableFrame> {

    public UpdateFrame(TableFrame topFrame) {
        super(topFrame);
    }

    @Override
    public String exportQL() {
        return null;
    }
}
