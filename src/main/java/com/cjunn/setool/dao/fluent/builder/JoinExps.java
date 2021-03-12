package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName JoinExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 11:20
 * @Version
 */
public class JoinExps extends BiExps {
    public JoinExps(Exps fir, Exps sec) {
        super(fir, sec);
    }

    @Override
    String getSplitChar() {
        return ",";
    }

}
