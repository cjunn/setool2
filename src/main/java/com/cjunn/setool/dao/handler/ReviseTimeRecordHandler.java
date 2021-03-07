package com.cjunn.setool.dao.handler;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.HandlerType;
import com.cjunn.setool.core.model.IHandler;
import com.cjunn.setool.utils.DateUtils;

import java.lang.reflect.Field;

/**
 * @ClassName @{NAME}
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/7 15:33
 * @Version
 */
public class ReviseTimeRecordHandler implements IHandler {
    @Override
    public void handler(HandlerType type, BaseModel model, Field field) throws IllegalAccessException {
        field.set(model, DateUtils.getDateTime14());
    }
}
