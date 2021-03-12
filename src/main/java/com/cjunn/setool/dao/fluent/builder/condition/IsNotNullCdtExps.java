package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;

import java.util.Collections;

/**
 * @ClassName ISNOTNULLConditionExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 10:20
 * @Version
 */
public class IsNotNullCdtExps extends ConditionExps {
    public IsNotNullCdtExps(QlField qlField) {
        super(qlField, QlConditionType.IS_NOT_NULL, Collections.emptyList());
    }
}

