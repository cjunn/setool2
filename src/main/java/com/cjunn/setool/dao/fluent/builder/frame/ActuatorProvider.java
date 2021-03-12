package com.cjunn.setool.dao.fluent.builder.frame;

public interface ActuatorProvider  extends IOptFrame<TableFrame>  {
    default TableFrame table(){
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
    default void execute(){
        TableFrame table = this.table();
        table.execute();
    }
}
