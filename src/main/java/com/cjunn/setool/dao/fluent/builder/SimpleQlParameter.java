package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName SimpleQlParameter
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 12:36
 * @Version
 */
public class SimpleQlParameter implements QlParameter {
    public SimpleQlParameter(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public String placeHolder;

    @Override
    public String getPlaceHolder() {
        return placeHolder;
    }
}
