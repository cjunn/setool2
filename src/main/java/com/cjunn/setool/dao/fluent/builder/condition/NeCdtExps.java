package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlParameter;

import java.util.List;

/**
 * @ClassName NeConditionExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 10:08
 * @Version
 */
public class NeCdtExps extends ConditionExps {
    public NeCdtExps(QlField qlField, List<QlParameter> qlParameters) {
        super(qlField, QlConditionType.NE, qlParameters);
    }
}