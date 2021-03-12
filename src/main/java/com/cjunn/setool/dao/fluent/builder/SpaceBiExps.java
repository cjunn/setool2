package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName SpaceBiExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 22:48
 * @Version
 */
public class SpaceBiExps extends BiExps {
    public SpaceBiExps(Exps fir) {
        super(fir);
    }
    public SpaceBiExps(Exps fir,Exps sec) {
        super(fir, sec);
    }

    @Override
    String getSplitChar() {
        return " ";
    }
}
