package com.cjunn.setool.dao.mybatis.mapper;


import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.mybatis.dto.BaseExample;
import com.cjunn.setool.dao.mybatis.intercept.ModelSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<PK extends Serializable, Model extends BaseModel, Example extends BaseExample> {
    @SelectProvider(type = ModelSQLProvider.class,method = "QueryByID")
    Model selectByPrimaryKey(@Param("id") PK var1);

    @SelectProvider(type = ModelSQLProvider.class,method = "DeleteByID")
    int deleteByPrimaryKey(@Param("id") PK var1);

    @InsertProvider(type = ModelSQLProvider.class,method = "InsertByModel")
    int insert(@Param("model") Model var1);

    @UpdateProvider(type = ModelSQLProvider.class,method = "UpdateByModel")
    int updateByPrimaryKey(@Param("model") Model var1);




    int countByExample(@Param("example") Example var1);
    int deleteByExample(@Param("example") Example var1);
    List<Model> selectByExample(@Param("example") Example var1);
    Model selectOneByExample(@Param("example") Example var1);


    int updateByExampleSelective(@Param("model") Model var1, @Param("example") Example var2);
    int updateByExample(@Param("model") Model var1, @Param("example") Example var2);


    Model selectByPrimaryKeyInMasterDb(@Param("id") PK var1);
    int updateByPrimaryKeyAndPartitionKey(@Param("model") Model var1);


    int insertSelective(@Param("model") Model var1);
    int updateByPrimaryKeySelective(@Param("model") Model var1);
}
