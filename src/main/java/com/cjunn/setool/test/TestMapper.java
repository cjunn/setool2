package com.cjunn.setool.test;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.mybatis.dto.AbstractBaseExample;
import com.cjunn.setool.dao.mybatis.dto.BaseExample;
import com.cjunn.setool.dao.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.io.Serializable;

/**
 * @ClassName TestMapper
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/8 21:03
 * @Version
 */
@Mapper
public interface TestMapper extends BaseMapper<String, TestMode, AbstractBaseExample>  {
    @Select("select * from test limit 1")
     TestMode findOne();


    int updateOne(@Param("p0") TestMode testMode);
}
