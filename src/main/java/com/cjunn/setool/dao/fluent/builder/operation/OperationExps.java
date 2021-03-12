package com.cjunn.setool.dao.fluent.builder.operation;

import com.cjunn.setool.dao.fluent.builder.QlTable;
import com.cjunn.setool.dao.fluent.builder.UnitExps;
import com.cjunn.setool.dao.fluent.builder.condition.ConditionExps;

/**
 * @ClassName OperationExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 11:00
 * @Version
 */
public abstract class OperationExps extends UnitExps {
    private QlTable qlTable;
    public OperationExps(QlTable qlTable,
                         ConditionExps fir) {
        super(fir);
        this.qlTable=qlTable;
    }
    public QlTable getQlTable() {
        return qlTable;
    }

    public void setQlTable(QlTable qlTable) {
        this.qlTable = qlTable;
    }
}
