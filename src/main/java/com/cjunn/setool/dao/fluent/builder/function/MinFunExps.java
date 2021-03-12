package com.cjunn.setool.dao.fluent.builder.function;

import com.cjunn.setool.dao.fluent.builder.Exps;

/**
 * @ClassName MinFunExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 11:31
 * @Version
 */
public  class MinFunExps  extends FunExps {
    public MinFunExps(Exps fir) {
        super(fir);
    }
    @Override
    public String exportQL() {
        return " MIN"+getFir().exportQL();
    }
}
