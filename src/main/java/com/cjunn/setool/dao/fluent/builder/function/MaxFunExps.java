package com.cjunn.setool.dao.fluent.builder.function;

import com.cjunn.setool.dao.fluent.builder.Exps;

/**
 * @ClassName MaxFunExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 11:30
 * @Version
 */
public class MaxFunExps extends FunExps {
    public MaxFunExps(Exps fir) {
        super(fir);
    }

    @Override
    public String exportQL() {
        return " MAX"+getFir().exportQL();
    }
}
