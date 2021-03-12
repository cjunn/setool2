package com.cjunn.setool.dao.fluent.builder.ext;

import com.cjunn.setool.dao.fluent.builder.Exps;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName Having
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 22:59
 * @Version
 */
public class HavingExps implements Exps {

    public HavingExps(){

    }
    public HavingExps(Exps exps){
        this.conditionExps=exps;
    }

    public Exps getConditionExps() {
        return conditionExps;
    }

    public void setConditionExps(Exps conditionExps) {
        this.conditionExps = conditionExps;
    }

    private Exps conditionExps;

    @Override
    public String exportQL() {
        return (conditionExps==null ||
                    StringUtils.isEmpty(StringUtils.trim(conditionExps.exportQL()))
                ) ?"":"HAVING " + conditionExps.exportQL();
    }
}
