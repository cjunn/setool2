package com.cjunn.setool.dao.fluent.builder.condition;

/**
 * @ClassName FalseCtdExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 13:12
 * @Version
 */
public class FalseCtdExps extends ConditionExps {
    public FalseCtdExps(){
        super(null, null, null);
    }

    @Override
    public String exportQL() {
        return " 1 = 2";
    }
}
