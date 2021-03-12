package com.cjunn.setool.dao.fluent.builder.condition;

import com.cjunn.setool.dao.fluent.builder.Exps;
import com.cjunn.setool.dao.fluent.builder.QlField;
import com.cjunn.setool.dao.fluent.builder.QlParameter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ConditionExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 9:47
 * @Version
 */
public abstract class ConditionExps<P extends QlParameter> implements Exps {
    private QlConditionType qlConditionType;
    private QlField qlField;
    private List<P> qlParameters;

    public ConditionExps( QlField qlField, QlConditionType qlConditionType,List<P> qlParameters) {
        this.qlConditionType = qlConditionType;
        this.qlField = qlField;
        this.qlParameters = qlParameters;
    }


    public QlConditionType getQlConditionType() {
        return qlConditionType;
    }

    public void setQlConditionType(QlConditionType qlConditionType) {
        this.qlConditionType = qlConditionType;
    }

    public QlField getQlField() {
        return qlField;
    }

    public void setQlField(QlField qlField) {
        this.qlField = qlField;
    }

    public List<P> getQlParameters() {
        return qlParameters;
    }

    public void setQlParameters(List<P> qlParameters) {
        this.qlParameters = qlParameters;
    }

    @Override
    public String exportQL(){
        return qlConditionType.join(
                qlField.getQlFiledName(),
                qlParameters.stream().map(QlParameter::getPlaceHolder).collect(Collectors.toList())
        );
    }

}
