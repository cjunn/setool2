package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName OrExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/9 20:41
 * @Version
 */
public class OrExps extends BiExps {
    public OrExps(){
        super(null,null);
    }
    public OrExps(Exps fir) {
        super(fir, null);
    }
    public OrExps(Exps fir,Exps sec) {
        super(fir, sec);
    }

    @Override
    String getSplitChar() {
        return "OR";
    }


}
