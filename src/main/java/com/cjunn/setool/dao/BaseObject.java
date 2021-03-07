package com.cjunn.setool.dao;

import com.cjunn.setool.core.model.BaseModel;
import com.cjunn.setool.dao.handler.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
@JsonIgnoreProperties
public class BaseObject extends BaseModel {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public Long getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Long recordVersion) {
        this.recordVersion = recordVersion;
    }

    public String getCreatorCode() {
        return creatorCode;
    }

    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReviserCode() {
        return reviserCode;
    }

    public void setReviserCode(String reviserCode) {
        this.reviserCode = reviserCode;
    }

    public String getReviserName() {
        return reviserName;
    }

    public void setReviserName(String reviserName) {
        this.reviserName = reviserName;
    }

    public String getReviseTime() {
        return reviseTime;
    }

    public void setReviseTime(String reviseTime) {
        this.reviseTime = reviseTime;
    }

    @Version
    @Column(
            name = "RECORD_VERSION",
            length = 10,
            precision = 10,
            scale = 0
    )
    protected Long recordVersion = 0L;

    @Column(
            name = "CREATOR_CODE",
            nullable = true,
            length = 50
    )
    @CreatorCodeRecord
    protected String creatorCode;


    @Column(
            name = "CREATOR_NAME",
            nullable = true,
            length = 50
    )
    @CreatorNameRecord
    protected String creatorName;


    @Column(
            name = "CREATE_TIME",
            nullable = true,
            length = 50
    )
    @CreateTimeRecord
    protected String createTime;


    @Column(
            name = "REVISER_CODE",
            nullable = true,
            length = 50
    )
    @ReviserCodeRecord
    protected String reviserCode;

    @Column(
            name = "REVISER_NAME",
            nullable = true,
            length = 50
    )
    @ReviserNameRecord
    protected String reviserName;


    @Column(
            name = "REVISE_TIME",
            nullable = true,
            length = 50
    )
    @ReviseTimeRecord
    protected String reviseTime;




}
