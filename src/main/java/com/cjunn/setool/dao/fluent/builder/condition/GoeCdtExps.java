package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlParameter;

import java.util.List;

/**
 * @ClassName GOE
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 10:18
 * @Version
 */
public class GoeCdtExps extends ConditionExps {
    public GoeCdtExps(QlField qlField, List<QlParameter> qlParameters) {
        super(qlField, QlConditionType.GOE, qlParameters);
    }
}