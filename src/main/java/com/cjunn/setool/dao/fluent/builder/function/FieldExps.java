package com.cjunn.setool.dao.fluent.builder.function;

import com.cjunn.setool.dao.fluent.builder.Exps;
import com.cjunn.setool.dao.fluent.builder.QlField;

/**
 * @ClassName FieldExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 11:26
 * @Version
 */
public class FieldExps implements Exps {
    public QlField getQlField() {
        return qlField;
    }

    public void setQlField(QlField qlField) {
        this.qlField = qlField;
    }

    public FieldExps(QlField qlField) {
        this.qlField = qlField;
    }

    private QlField qlField;

    @Override
    public String exportQL() {
        return qlField.getQlFiledName();
    }
}
