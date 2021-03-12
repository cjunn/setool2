package com.cjunn.setool.dao.fluent.builder.ql;

import com.cjunn.setool.dao.fluent.builder.Exps;
import com.cjunn.setool.dao.fluent.builder.UnitExps;

/**
 * @ClassName WhereOpsExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 11:12
 * @Version
 */
public class WhereExps extends UnitExps {
    public WhereExps(Exps fir) {
        super(fir);
    }
    @Override
    public String exportQL() {
        return "WHERE " +getFir().exportQL();
    }
}
