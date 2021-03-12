package com.cjunn.setool.dao.fluent.support.mybatis;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.core.model.JpaAnnoModeInfo;
import com.cjunn.setool.dao.fluent.builder.frame.TableFrame;
import com.cjunn.setool.dao.mybatis.intercept.ModelSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @ClassName MybsMapper
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/12 13:14
 * @Version
 */
public interface MybsMapper {
    @SelectProvider(type = MybsSqlCfg.class,method = "exportQL")
    <T extends BaseModel>List<T> select(@Param("param") TableFrame<T> tableFrame);

    @SelectProvider(type = MybsSqlCfg.class,method = "exportQL")
    <T extends BaseModel> T selectOne(@Param("param") TableFrame<T> tableFrame);

    @SelectProvider(type = MybsSqlCfg.class,method = "exportQL")
    <T extends BaseModel> Integer countCmd(@Param("param") TableFrame<T> tableFrame);

    @SelectProvider(type = MybsSqlCfg.class,method = "exportQL")
    <T extends BaseModel> Integer deleteCmd(@Param("param") TableFrame<T> tableFrame);

    @SelectProvider(type = MybsSqlCfg.class,method = "exportQL")
    <T extends BaseModel> Integer updateModel(@Param("param") TableFrame<T> tableFrame);

    @SelectProvider(type = MybsSqlCfg.class,method = "exportQL")
    <T extends BaseModel> Integer updateWhere(@Param("param") TableFrame<T> tableFrame);

    @SelectProvider(type = MybsSqlCfg.class,method = "exportQL")
    <T extends BaseModel> Integer deleteByPk(@Param("param") TableFrame<T> tableFrame);

    @SelectProvider(type = MybsSqlCfg.class,method = "exportQL")
    <T extends BaseModel> Integer deleteWhere(@Param("param") TableFrame<T> tableFrame);

}
