package com.cjunn.setool.dao.fluent.builder.function;

import com.cjunn.setool.dao.fluent.builder.Exps;

/**
 * @ClassName SumFunExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 11:25
 * @Version
 */
public class SumFunExps extends FunExps {
    public SumFunExps(Exps fir) {
        super(fir);
    }

    @Override
    public String exportQL() {
        return " SUM " + getFir().exportQL();
    }
}
