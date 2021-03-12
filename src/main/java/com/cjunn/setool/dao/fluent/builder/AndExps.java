package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName AndExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/9 20:41
 * @Version
 */
public class AndExps extends BiExps {

    public AndExps(){
        super(null, null);
    }

    public AndExps(Exps fir) {
        super(fir, null);
    }
    public AndExps(Exps fir,Exps sec) {
        super(fir, sec);
    }

    @Override
    String getSplitChar() {
        return "AND";
    }

}
