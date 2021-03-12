package com.cjunn.setool.dao.mybatis.intercept;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.HandlerAnnoModelInfo;
import com.cjunn.setool.core.model.HandlerType;
import com.cjunn.setool.core.model.JpaAnnoModeInfo;
import com.cjunn.setool.dao.IdMaker;
import com.cjunn.setool.dao.mybatis.mapper.BaseMapperUtils;
import com.cjunn.setool.dao.fluent.ISQLGenerator;
import com.cjunn.setool.dao.fluent.MysqlSQLGenerator;
import com.cjunn.setool.utils.Reflections;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName SQLTextGenerator
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/9 9:27
 * @Version
 */
public class ModelSQLProvider {
    private final static ConcurrentHashMap<Method,String> COMPILE_SQL_CAHCE=new ConcurrentHashMap<>();
    private ISQLGenerator sqlGenerator=new MysqlSQLGenerator();

    public static JpaAnnoModeInfo getJpaFromPc(ProviderContext context){
        return JpaAnnoModeInfo.of(BaseMapperUtils.getGenericModelType(context.getMapperType()));
    }

    public String QueryByID(ProviderContext context){
        return COMPILE_SQL_CAHCE.computeIfAbsent(
                context.getMapperMethod(),
                (m)-> sqlGenerator.makeQueryByIdSql(
                        getJpaFromPc(context),
                        (pair -> "#{id}")
                     )
        );
    }


    public String DeleteByID(ProviderContext context){
        return COMPILE_SQL_CAHCE.computeIfAbsent(
                context.getMapperMethod(),
                (m)-> sqlGenerator.makeDeleteByIdSql(
                        getJpaFromPc(context),
                        (pair -> "#{id}")
                    )
        );
    }

    public String InsertByModel(ProviderContext context,Map map){
        BaseModel baseModel = (BaseModel) map.get("model");
        JpaAnnoModeInfo modelInfo = getJpaFromPc(context);
        Object pkValue = Reflections.getFieldValue(baseModel, modelInfo.getPkFieldName());
        if (pkValue == null) {
            pkValue = IdMaker.get(modelInfo);
            Reflections.setFieldValue(baseModel,modelInfo.getPkFieldName(),pkValue);
        }
        HandlerAnnoModelInfo.doFieldHandler(Arrays.asList(baseModel), HandlerType.INSERT);
        return COMPILE_SQL_CAHCE.computeIfAbsent(
                context.getMapperMethod(),
                (m) -> sqlGenerator.makeSaveSql(1,
                        modelInfo,
                        (pair -> "#{model."+pair.getValue1()+"}")
                    )
        );
    }

    public String UpdateByModel(ProviderContext context,Map map){
        BaseModel baseModel = (BaseModel) map.get("model");
        JpaAnnoModeInfo modelInfo = getJpaFromPc(context);
        HandlerAnnoModelInfo.doFieldHandler(Arrays.asList(baseModel), HandlerType.INSERT);
        return COMPILE_SQL_CAHCE.computeIfAbsent(
                context.getMapperMethod(),
                (m) -> sqlGenerator.makeUpdateSql(
                        modelInfo,
                        (pair -> "#{model."+pair.getValue1()+"}")
                )
        );
    }

}
