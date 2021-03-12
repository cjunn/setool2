package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlParameter;

import java.util.List;

/**
 * @ClassName LtConditionExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 10:10
 * @Version
 */
public class LtCdtExps extends ConditionExps {
    public LtCdtExps(QlField qlField, List<QlParameter> qlParameters) {
        super(qlField, QlConditionType.LT, qlParameters);
    }
}