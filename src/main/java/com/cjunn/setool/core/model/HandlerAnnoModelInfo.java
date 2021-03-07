package com.cjunn.setool.core.model;

import com.cjunn.setool.core.util.ContextHolder;
import com.cjunn.setool.utils.AnnoUtils;
import com.cjunn.setool.utils.Predicates2;
import com.cjunn.setool.utils.Reflections;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerAnnoModelInfo {
    private Map<Class<?>,Iterable<HandlerAnnoModelInfo.HandlerAnnoField>> CLZ_HANDLER_CAHCE=new ConcurrentHashMap<>();
    private final Iterable<HandlerAnnoModelInfo.HandlerAnnoField> handlerAnnotatedFields;

    public HandlerAnnoModelInfo(Class<?> entityClazz) {
        this.handlerAnnotatedFields = this.searchHandlerAnnotatedFields(entityClazz);
    }
    private Iterable<HandlerAnnoModelInfo.HandlerAnnoField> searchHandlerAnnotatedFields(Class<?> entityClazz) {
        Iterable<HandlerAnnoModelInfo.HandlerAnnoField> iterable = CLZ_HANDLER_CAHCE.get(entityClazz);
        if(iterable!=null){
            return iterable;
        }
        try{
            List<Field> allFields = FieldUtils.getAllFieldsList(entityClazz);
            List<HandlerAnnoModelInfo.HandlerAnnoField> infos=new ArrayList<>();
            for(Field field:allFields){
                List<Handler> handlers = (List<Handler>) AnnoUtils.lookupAnnoFromField(field, Handler.class);
                if(handlers==null||handlers.size()==0){
                    continue;
                }
                Class<? extends IHandler> iHandlerClz = handlers.get(0).value();
                IHandler iHandler = iHandlerClz.newInstance();
                infos.add(new HandlerAnnoModelInfo.HandlerAnnoField(field,iHandler));
            }
            CLZ_HANDLER_CAHCE.put(entityClazz,infos);
        }catch (Exception ex){
            throw new IllegalStateException(ex);
        }
        return CLZ_HANDLER_CAHCE.get(entityClazz);
    }


    public static HandlerAnnoModelInfo of(Class<?> entityClazz) {
        return new HandlerAnnoModelInfo(entityClazz);
    }

    public void doHandler(HandlerType handlerType, BaseModel entity) {
        try {
            Iterator var3 = this.handlerAnnotatedFields.iterator();
            while(var3.hasNext()) {
                HandlerAnnoModelInfo.HandlerAnnoField handlerAnnotatedField = (HandlerAnnoModelInfo.HandlerAnnoField)var3.next();
                Field field = handlerAnnotatedField.field;
                Reflections.makeAccessible(field);
                handlerAnnotatedField.handler.handler(handlerType, entity, field);
            }

        } catch (IllegalAccessException var6) {
            throw new RuntimeException(var6);
        }
    }

    private class HandlerAnnoField {
        private Field field;
        private IHandler handler;
        public HandlerAnnoField(Field field,IHandler handler) {
            this.field = field;
            this.handler=handler;
        }
        public Field getField() {
            return this.field;
        }
        public IHandler getHandler() {
            return this.handler;
        }
    }
}
