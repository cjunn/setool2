package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.fluent.builder.Exps;
import com.cjunn.setool.dao.fluent.builder.RightBiExps;
import com.cjunn.setool.dao.fluent.builder.ext.CountOneExps;
import com.cjunn.setool.dao.fluent.builder.ext.SelectAllExps;
import org.apache.commons.lang3.ClassUtils;

import javax.persistence.Table;

/**
 * @ClassName SelectFrame
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:03
 * @Version
 */
public class SelectFrame<T extends BaseModel> extends AbstractOptFrame<TableFrame> implements
                                                                                        SelectFrameProvider<T> ,
                                                                                        WhereFrameProvider<T>{
    private String tableName;
    private Exps item;
    private RightBiExps fieldBiExps;
    public SelectFrame(TableFrame topFrame, Class<?> modelClazz) {
        super(topFrame);
        tableName=modelClazz.isAnnotationPresent(Table.class)?modelClazz.getAnnotation(Table.class).name():null;
        if(tableName==null){
            throw new IllegalStateException(ClassUtils.getSimpleName(modelClazz)+" 没有未被 javax.persistence.Table 注解,不符合预期");
        }
    }

    public SelectFrame<T> addField(){
        return this;
    }
    public SelectFrame<T> count(){
        item=new CountOneExps();
        return this;
    }
    public SelectFrame<T> all(){
        item=new SelectAllExps();
        return this;
    }
    @Override
    public String exportQL() {
        return new StringBuilder()
                .append(item==null?fieldBiExps.exportQL():item.exportQL())
                .append(" FROM ")
                .append(tableName)
                .toString();
    }
}
