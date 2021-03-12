package com.cjunn.setool.dao.fluent.builder.condition;

/**
 * @ClassName EmptyCtdExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 12:10
 * @Version
 */
public class TrueCtdExps extends ConditionExps {
    public TrueCtdExps(){
        super(null, null, null);
    }

    @Override
    public String exportQL() {
        return " 1 = 1";
    }
}
