package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlParameter;

import java.util.List;

/**
 * @ClassName EqConfitionExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 9:48
 * @Version
 */
public class EqCdtExps extends ConditionExps {
    public EqCdtExps(QlField qlField, List<QlParameter> qlParameters) {
        super(qlField, QlConditionType.EQ, qlParameters);
    }
}
