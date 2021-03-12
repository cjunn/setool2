package com.cjunn.setool.dao.fluent.support.mybatis;

import com.cjunn.setool.dao.fluent.builder.frame.TableFrame;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @ClassName Mybatis
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/12 13:16
 * @Version
 */
public class MybsSqlCfg {
    public String exportQL(Map map){
        TableFrame tableFrame = (TableFrame) map.get("param");
        map.clear();
        map.putAll(tableFrame.getPlaceHolderAndValue());
        return tableFrame.exportQL();
    }
}
