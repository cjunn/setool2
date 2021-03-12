package com.cjunn.setool.dao.fluent.builder;

/**
 * @ClassName SimpleQlField
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/10 12:34
 * @Version
 */
public class SimpleQlField implements QlField {
    private String qlFiledName;

    public SimpleQlField(String qlFiledName){
        this.qlFiledName=qlFiledName;
    }

    @Override
    public String getQlFiledName() {
        return qlFiledName;
    }
}
