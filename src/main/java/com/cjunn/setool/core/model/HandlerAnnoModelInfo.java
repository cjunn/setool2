package com.cjunn.setool.core.model;

import com.cjunn.setool.utils.AnnoUtils;
import com.cjunn.setool.utils.Reflections;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerAnnoModelInfo {
    private final static Map<Class<?>,Iterable<HandlerAnnoModelInfo.HandlerAnnoField>> CLZ_HANDLER_CAHCE=new ConcurrentHashMap<>();
    private final Iterable<HandlerAnnoModelInfo.HandlerAnnoField> handlerAnnotatedFields;

    public static  <T extends BaseModel> void doFieldHandler(Iterable<T> source, HandlerType handlerType) {
        if (!Iterables.isEmpty(source)) {
            Class<? extends BaseModel> entityClazz = ((BaseModel)source.iterator().next()).getClass();
            HandlerAnnoModelInfo hami = HandlerAnnoModelInfo.of(entityClazz);
            Iterator<T> iterator = source.iterator();
            while(iterator.hasNext()) {
                hami.doHandler(handlerType, iterator.next());
            }
        }
    }

    public HandlerAnnoModelInfo(Class<?> entityClazz) {
        this.handlerAnnotatedFields = this.searchHandlerAnnotatedFields(entityClazz);
    }
    private Iterable<HandlerAnnoModelInfo.HandlerAnnoField> searchHandlerAnnotatedFields(Class<?> entityClazz) {
        return CLZ_HANDLER_CAHCE.computeIfAbsent(entityClazz,(clz)->{
            List<HandlerAnnoModelInfo.HandlerAnnoField> infos=new ArrayList<>();
            try{
                List<Field> allFields = FieldUtils.getAllFieldsList(clz);
                for(Field field:allFields){
                    List<Handler> handlers = (List<Handler>) AnnoUtils.lookupAnnoFromField(field, Handler.class);
                    if(handlers==null||handlers.size()==0){
                        continue;
                    }
                    Class<? extends IHandler> iHandlerClz = handlers.get(0).value();
                    IHandler iHandler = iHandlerClz.newInstance();
                    infos.add(new HandlerAnnoModelInfo.HandlerAnnoField(field,iHandler));
                }
            }catch (Exception ex){
                throw new IllegalStateException(ex);
            }
            return infos;
        });
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
