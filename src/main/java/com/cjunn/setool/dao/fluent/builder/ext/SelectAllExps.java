package com.cjunn.setool.dao.fluent.builder.ext;

import com.cjunn.setool.dao.fluent.builder.Exps;

/**
 * @ClassName SelectAllExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/12 22:27
 * @Version
 */
public class SelectAllExps implements Exps {
    @Override
    public String exportQL() {
        return "SELECT *";
    }
}
