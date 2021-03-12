package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName UniExpression
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/9 20:30
 * @Version
 */
public abstract class UnitExps implements Exps {
    private Exps fir;
    public UnitExps(Exps fir){
        this.fir=fir;
    }
    public Exps getFir() {
        return fir;
    }
    public void setFir(Exps fir) {
        this.fir = fir;
    }
}
