package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName RightBiExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 21:30
 * @Version
 */

//只关注右侧表达式的双元单位
public class RightBiExps extends BiExps {
    public RightBiExps(){
        super(null,null);
    }
    public RightBiExps(Exps sec){
        super(null,null);
        this.setSec(sec);
    }
    @Override
    String getSplitChar() {
        return "";
    }
    @Override
    public String exportQL() {
        return
                getSec()==null?"":
                getSec().exportQL();
    }
}
