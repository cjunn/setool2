package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName BracketExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 9:46
 * @Version
 */
public class BracketExps extends UnitExps {
    public BracketExps(Exps fir) {
        super(fir);
    }
    @Override
    public String exportQL() {
        return "( "+ getFir().exportQL() +" )";
    }
}
