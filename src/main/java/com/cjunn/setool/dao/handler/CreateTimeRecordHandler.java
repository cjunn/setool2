package com.cjunn.setool.dao.handler;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.HandlerType;
import com.cjunn.setool.core.model.IHandler;
import com.cjunn.setool.dao.BaseObject;
import com.cjunn.setool.utils.DateUtils;

import java.lang.reflect.Field;

/**
 * @ClassName CreateTimeRecordHandler
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/7 15:31
 * @Version
 */
public class CreateTimeRecordHandler implements IHandler {
    @Override
    public void handler(HandlerType type, BaseModel model, Field field) throws IllegalAccessException {
        if(HandlerType.INSERT.equals(type)){
            field.set(model, DateUtils.getDateTime14());
        }
    }
}
