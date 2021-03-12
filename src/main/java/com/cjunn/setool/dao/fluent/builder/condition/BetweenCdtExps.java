package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlParameter;

import java.util.List;

/**
 * @ClassName BETWEENConditionExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 10:23
 * @Version
 */
public class BetweenCdtExps extends ConditionExps {
    public <T extends QlParameter>  BetweenCdtExps(QlField qlField, List<T> qlParameters) {
        super(qlField, QlConditionType.BETWEEN, qlParameters);
    }
}

