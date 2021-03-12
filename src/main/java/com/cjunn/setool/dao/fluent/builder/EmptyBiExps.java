package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName StartBiExps
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 21:04
 * @Version
 */
public class EmptyBiExps extends BiExps {
    public EmptyBiExps() {
        super(null);
    }
    @Override
    String getSplitChar() {
        return "";
    }
}
