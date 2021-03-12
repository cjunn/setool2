package com.cjunn.setool.dao.fluent.builder.ext;

import com.cjunn.setool.dao.fluent.builder.Exps;

/**
 * @ClassName CountExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/12 22:27
 * @Version
 */
public class CountOneExps implements Exps {
    @Override
    public String exportQL() {
        return "COUNT(1)";
    }
}
