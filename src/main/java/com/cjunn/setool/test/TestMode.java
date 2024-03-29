package com.cjunn.setool.test;

import com.cjunn.setool.core.generator.Generator;
import com.cjunn.setool.dao.BaseObject;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="test")
public class TestMode extends BaseObject {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    @Generator("snowFlakeGenerator")
    private String id;

    public String getTtt() {
        return ttt;
    }

    public void setTtt(String ttt) {
        this.ttt = ttt;
    }

    @Column(name = "TTT")
    private String ttt="bbb";
}
