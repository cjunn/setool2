package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlParameter;

import java.util.List;

/**
 * @ClassName Like
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 10:25
 * @Version
 */
public class LikeCdtExps extends ConditionExps {
    public LikeCdtExps(QlField qlField, List<QlParameter> qlParameters) {
        super(qlField, QlConditionType.LIKE, qlParameters);
    }
}