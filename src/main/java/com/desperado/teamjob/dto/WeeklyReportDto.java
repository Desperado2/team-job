package com.desperado.teamjob.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class WeeklyReportDto {
    @ApiModelProperty("总人数")
    private Integer userCount;
    @ApiModelProperty("已提交人数")
    private Integer commitCount;
    @ApiModelProperty("未提交用户")
    private List<UserDto> notCommitUsers;

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(Integer commitCount) {
        this.commitCount = commitCount;
    }

    public List<UserDto> getNotCommitUsers() {
        return notCommitUsers;
    }

    public void setNotCommitUsers(List<UserDto> notCommitUsers) {
        this.notCommitUsers = notCommitUsers;
    }
}
