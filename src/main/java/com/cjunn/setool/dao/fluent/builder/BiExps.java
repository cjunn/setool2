package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName BiExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/9 20:37
 * @Version
 */
public abstract class BiExps extends UnitExps {
    private Exps sec;
    public BiExps(Exps fir) {
        super(fir);
        this.sec=sec;
    }
    public BiExps(Exps fir, Exps sec) {
        super(fir);
        this.sec=sec;
    }
    abstract String getSplitChar();
    @Override
    public String exportQL() {
        if(getFir()==null && getSec()==null){
            return "";
        }
        if(getFir()!=null && getSec()==null){
            return getFir().exportQL();
        }
        if(getFir()==null && getSec()!=null){
            return getSec().exportQL();
        }
        if(" ".equals(getSplitChar())){
            return getFir().exportQL()+" " +getSec().exportQL();
        }
        return getFir().exportQL()+" "+getSplitChar()+" " +getSec().exportQL();
    }




    public Exps getSec() {
        return sec;
    }

    public void setSec(Exps sec) {
        this.sec = sec;
    }

    public <T extends BiExps> T accept(T sec){
        this.sec = sec;
        return sec;
    }


}
