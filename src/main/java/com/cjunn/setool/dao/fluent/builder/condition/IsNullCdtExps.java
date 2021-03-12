package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;

import java.util.Collections;

/**
 * @ClassName ISNULL
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 10:19
 * @Version
 */
public class IsNullCdtExps extends ConditionExps {
    public IsNullCdtExps(QlField qlField) {
        super(qlField, QlConditionType.IS_NULL, Collections.emptyList());
    }
}
