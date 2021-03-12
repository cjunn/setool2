package com.cjunn.setool.dao.fluent.builder.frame;

import com.cjunn.setool.core.model.BaseModel;

import java.util.List;

public interface ActuatorProvider<T extends BaseModel>  extends IOptFrame<TableFrame>  {
    default TableFrame<T> table(){
        if(this.topFrame()!=null){
            return this.topFrame();
        }
        if(this instanceof TableFrame){
            return (TableFrame) this;
        }
        return null;
    }
    default String createQL(){
        TableFrame table = this.table();
        return table==null?"":table.exportQL();
    }


    /*触发执行*/
    default List<T> selectCmd(){
        return this.table().selectCmd();
    }
    default T selectOneCmd(){
        return this.table().selectOneCmd();
    }
    default Integer countCmd(){
        return this.table().countCmd();
    }
    default Integer updateModel(){
        return this.table().updateModel();
    }
    default Integer updateWhere(){
        return this.table().updateWhere();
    }

    default Integer deleteByPk(){
        return this.table().updateWhere();
    }

    default Integer deleteWhere(){
        return this.table().updateWhere();
    }



}
