package com.cjunn.setool.core.model;

import java.lang.reflect.Field;

public interface IHandler {
    void handler(HandlerType type, BaseModel model, Field field) throws IllegalAccessException;
}
