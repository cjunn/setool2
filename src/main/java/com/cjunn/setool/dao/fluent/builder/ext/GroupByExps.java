package com.cjunn.setool.dao.fluent.builder.ext;

import com.cjunn.setool.dao.fluent.builder.Exps;
import com.cjunn.setool.dao.fluent.builder.QlField;
import com.google.common.base.Joiner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName GroupBy
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 22:58
 * @Version
 */
public class GroupByExps<Q extends QlField> implements Exps {
    public GroupByExps(){

    }
    public GroupByExps(List<Q> qlFields){
        this.qlFields=qlFields;
    }
    public List<Q> getQlFields() {
        return qlFields;
    }

    public void setQlFields(List<Q> qlFields) {
        this.qlFields = qlFields;
    }

    private List<Q> qlFields;

    @Override
    public String exportQL() {
        return (qlFields==null||qlFields.size()==0)?"":"GROUP BY "+ Joiner.on(',').join(qlFields.stream().map(QlField::getQlFiledName).collect(Collectors.toList()));
    }
}
