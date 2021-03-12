package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.dao.fluent.builder.RightBiExps;
import org.apache.commons.lang3.ClassUtils;

import javax.persistence.Table;

/**
 * @ClassName SelectFrame
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/11 14:03
 * @Version
 */
public class SelectFrame extends AbstractOptFrame<TableFrame> implements SelectFrameProvider ,WhereFrameProvider{
    private String tableName;
    private RightBiExps lineFieldBiExps=new RightBiExps();
    public SelectFrame(TableFrame topFrame, Class<?> modelClazz) {
        super(topFrame);
        tableName=modelClazz.isAnnotationPresent(Table.class)?modelClazz.getAnnotation(Table.class).name():null;
        if(tableName==null){
            throw new IllegalStateException(ClassUtils.getSimpleName(modelClazz)+" 没有未被 javax.persistence.Table 注解,不符合预期");
        }
    }

    public SelectFrame addField(){
        return this;
    }

    @Override
    public String exportQL() {
        return "SELECT * FROM "+tableName;
    }
}
