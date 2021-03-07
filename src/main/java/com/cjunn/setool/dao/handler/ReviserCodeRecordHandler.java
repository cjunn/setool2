package com.cjunn.setool.dao.handler;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.HandlerType;
import com.cjunn.setool.core.model.IHandler;
import com.cjunn.setool.core.user.UserContextHolder;

import java.lang.reflect.Field;

/**
 * @ClassName ReviserCodeRecordHandler
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/7 15:56
 * @Version
 */
public class ReviserCodeRecordHandler implements IHandler {
    @Override
    public void handler(HandlerType type, BaseModel model, Field field) throws IllegalAccessException {
        field.set(model, UserContextHolder.getUser().getUserCode());
    }
}
