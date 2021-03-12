package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlParameter;

import java.util.List;

/**
 * @ClassName GTConditionExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 10:18
 * @Version
 */
public class GtCdtExps extends ConditionExps {
    public GtCdtExps(QlField qlField, List<QlParameter> qlParameters) {
        super(qlField, QlConditionType.GT, qlParameters);
    }
}