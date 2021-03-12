package com.cjunn.setool.dao.mybatis.mapper;

import com.cjunn.setool.core.model.BaseModel;

import java.lang.reflect.ParameterizedType;

/**
 * @ClassName BaseMapperUtils
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/9 12:10
 * @Version
 */
public class BaseMapperUtils {
    public static <T extends BaseModel> Class<T> getGenericModelType(Class<?> clz){
        if(!BaseMapper.class.isAssignableFrom(clz)){
            return null;
        }
        return (Class<T>) ((ParameterizedType)clz.getGenericInterfaces()[0]).getActualTypeArguments()[1];
    }
}
