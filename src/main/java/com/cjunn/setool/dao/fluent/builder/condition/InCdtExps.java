package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlParameter;

import java.util.List;

/**
 * @ClassName InConditionExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 10:25
 * @Version
 */
public class InCdtExps extends ConditionExps {
    public <T extends QlParameter> InCdtExps(QlField qlField, List<T> qlParameters) {
        super(qlField, QlConditionType.IN, qlParameters);
    }
}

