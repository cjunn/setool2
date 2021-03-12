package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;

/**
 * @ClassName DeleteFrame
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:38
 * @Version
 */
public class DeleteFrame<T extends BaseModel> extends AbstractOptFrame<TableFrame> {

    public DeleteFrame(TableFrame topFrame) {
        super(topFrame);
    }

    @Override
    public String exportQL() {
        return null;
    }
}
