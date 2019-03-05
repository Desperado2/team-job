package com.desperado.teamjob.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel
public class WeeklyDto implements Serializable {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户头像url")
    private String headurl;
    @ApiModelProperty("本周工作")
    private String thisWeekReport;
    @ApiModelProperty("下周工作")
    private String nextWeekReport;
    @ApiModelProperty("感想")
    private String feeling;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
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

}
