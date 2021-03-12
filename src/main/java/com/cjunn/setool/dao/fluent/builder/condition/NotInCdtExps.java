package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlParameter;

import java.util.List;

/**
 * @ClassName NotIn
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 10:25
 * @Version
 */
public class NotInCdtExps extends ConditionExps {
    public <T extends QlParameter> NotInCdtExps(QlField qlField, List<T> qlParameters) {
        super(qlField, QlConditionType.NOT_IN, qlParameters);
    }
}