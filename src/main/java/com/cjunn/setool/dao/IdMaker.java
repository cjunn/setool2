package com.cjunn.setool.dao;

import com.cjunn.setool.core.generator.Generator;
import com.cjunn.setool.core.generator.IGenerator;
import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.JpaAnnoModeInfo;
import com.cjunn.setool.core.util.ContextHolder;
import com.cjunn.setool.utils.Identities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class IdMaker {
    public static final Logger LOGGER = LoggerFactory.getLogger(IdMaker.class);

    public IdMaker() {
    }

    public static <T extends BaseModel, PK> PK get(Class<T> modelClass) {
        JpaAnnoModeInfo jami = JpaAnnoModeInfo.of(modelClass);
        return (PK) get(jami);
    }

    public static Object generator2Id(Object generator,Field pkField){
        if(generator.getClass().equals(pkField.getType())){
            return generator;
        }
        Class<?> type = pkField.getType();
        try{
            if(String.class.equals(type)){
                return String.valueOf(generator);
            }else if(Long.class.equals(type)){
                return Long.valueOf(generator.toString());
            }else if(Integer.class.equals(type)){
                return Integer.valueOf(generator.toString());
            }else if(Double.class.equals(type)){
                return Double.valueOf(generator.toString());
            }
        }catch (NumberFormatException ex){
            LOGGER.error("主键转换异常"+generator+","+pkField.getType());
        }
        throw new IllegalStateException("主键生成器类型转换失败异常"+generator+","+pkField.getType());
    }


    public static Object get(JpaAnnoModeInfo jami) {
        Class<?> modelClazz = jami.getModelClazz();
        if (modelClazz == null) {
            throw new IllegalArgumentException("JPA" + jami.toString() + "信息不包含Model信息， 无法生成ID");
        } else {
            Field pkField = jami.getPkField();
            if (pkField != null && pkField.isAnnotationPresent(Generator.class)) {
                Generator generatorAnnotation = pkField.getAnnotation(Generator.class);
                String generatorInstanceName = generatorAnnotation.value();
                if (ContextHolder.appContext.containsBean(generatorInstanceName)) {
                    IGenerator<?> generator = (IGenerator)ContextHolder.appContext.getBean(generatorInstanceName, IGenerator.class);
                    return generator2Id(generator.generate(modelClazz),pkField);
                } else {
                    throw new RuntimeException(String.format("未能在Spring容器中找到名称为 : %s 的 IGenerator 实例", generatorInstanceName));
                }
            } else {
                LOGGER.warn("模型 {} 没有配置ID生成器 , 默认使用UUID", modelClazz);
                return Identities.uuid2();
            }
        }
    }
}
