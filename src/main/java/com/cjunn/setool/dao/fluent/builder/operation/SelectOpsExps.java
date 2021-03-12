package com.cjunn.setool.dao.fluent.builder.operation;

import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlTable;
import com.cjunn.setool.dao.fluent.builder.condition.ConditionExps;
import com.google.common.base.Joiner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SelectOpsExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 11:06
 * @Version
 */
public class SelectOpsExps extends OperationExps {
    List<QlField> qlFieldList;

    public SelectOpsExps(QlTable qlTable, ConditionExps fir) {
        super(qlTable, fir);
    }

    @Override
    public String exportQL() {
        StringBuffer sBuffer=new StringBuffer();
        sBuffer.append("SELECT ");
        sBuffer.append(Joiner.on(',').join(qlFieldList.stream().map(QlField::getQlFiledName).collect(Collectors.toList())));
        sBuffer.append(" FROM ");
        sBuffer.append(this.getQlTable().getQlTableName());
        return sBuffer.toString();
    }
}
