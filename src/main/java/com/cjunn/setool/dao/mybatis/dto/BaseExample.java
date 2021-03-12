package com.cjunn.setool.dao.mybatis.dto;

/**
 * @ClassName BaseExample
 * @Description TODO
 * @Author cjunn
 * @Date 2021/3/9 8:55
 * @Version
 */
public abstract class BaseExample {
    protected Page page;

    public BaseExample() {
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return this.page;
    }
}
