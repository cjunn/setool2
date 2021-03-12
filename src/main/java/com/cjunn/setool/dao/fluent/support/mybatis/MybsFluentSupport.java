package com.cjunn.setool.dao.fluent.support.mybatis;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.fluent.builder.frame.TableFrame;
import com.cjunn.setool.dao.fluent.support.AbstractFluentSupport;
import com.cjunn.setool.test.TestMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName MybsFluentSupport
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/12 15:49
 * @Version
 */
public class MybsFluentSupport extends AbstractFluentSupport {
    private MybsMapper mybsMapper;
    public MybsFluentSupport(MybsMapper mybsMapper){
        this.mybsMapper=mybsMapper;
    }


    @Override
    public <T extends BaseModel> List<T> selectCmd(TableFrame<T> tableFrame) {
        return mybsMapper.select(tableFrame);
    }

    @Override
    public <T extends BaseModel> T selectOneCmd(TableFrame<T> tableFrame) {
        return mybsMapper.selectOne(tableFrame);
    }

    @Override
    public <T extends BaseModel> Integer countCmd(TableFrame<T> tableFrame) {
        return mybsMapper.countCmd(tableFrame);
    }

    @Override
    public <T extends BaseModel> Integer updateModel(TableFrame<T> tableFrame) {
        return mybsMapper.updateModel(tableFrame);
    }

    @Override
    public <T extends BaseModel> Integer updateWhere(TableFrame<T> tableFrame) {
        return mybsMapper.updateWhere(tableFrame);
    }

    @Override
    public <T extends BaseModel> Integer deleteByPk(TableFrame<T> tableFrame) {
        return mybsMapper.deleteByPk(tableFrame);
    }

    @Override
    public <T extends BaseModel> Integer deleteWhere(TableFrame<T> tableFrame) {
        return mybsMapper.deleteWhere(tableFrame);
    }


}
