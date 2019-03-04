package com.desperado.teamjob.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel
public class Weekly implements Serializable {
    @ApiModelProperty("周报id")
    private String id;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("周数")
    private Integer week;
    @ApiModelProperty("本周工作")
    private String thisWeekReport;
    @ApiModelProperty("下周工作")
    private String nextWeekReport;
    @ApiModelProperty("感想")
    private String feeling;
    @ApiModelProperty("创建时间")
    private Date dateCreate;
    @ApiModelProperty("更新时间")
    private Date dateUpdate;
    private String group;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getThisWeekReport() {
        return thisWeekReport;
    }

    public void setThisWeekReport(String thisWeekReport) {
        this.thisWeekReport = thisWeekReport;
    }

    public String getNextWeekReport() {
        return nextWeekReport;
    }

    public void setNextWeekReport(String nextWeekReport) {
        this.nextWeekReport = nextWeekReport;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
