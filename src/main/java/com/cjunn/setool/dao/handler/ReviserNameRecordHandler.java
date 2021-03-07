package com.cjunn.setool.dao.handler;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.HandlerType;
import com.cjunn.setool.core.model.IHandler;
import com.cjunn.setool.core.user.UserContextHolder;

import java.lang.reflect.Field;

/**
 * @ClassName ReviserNameRecordHandler
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/7 15:57
 * @Version
 */
public class ReviserNameRecordHandler implements IHandler {
    @Override
    public void handler(HandlerType type, BaseModel model, Field field) throws IllegalAccessException {
        field.set(model, UserContextHolder.getUser().getUserName());
    }
}
