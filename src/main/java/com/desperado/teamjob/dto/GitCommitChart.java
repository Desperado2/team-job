package com.desperado.teamjob.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class GitCommitChart {
    @ApiModelProperty("用户名称")
    List<String> users;
    @ApiModelProperty("增加行数")
    List<Integer> addLines;
    @ApiModelProperty("删除行数")
    List<Integer> delLines;
    @ApiModelProperty("提交次数")
    List<Integer> commits;

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<Integer> getAddLines() {
        return addLines;
    }

    public void setAddLines(List<Integer> addLines) {
        this.addLines = addLines;
    }

    public List<Integer> getDelLines() {
        return delLines;
    }

    public void setDelLines(List<Integer> delLines) {
        this.delLines = delLines;
    }

    public List<Integer> getCommits() {
        return commits;
    }

    public void setCommits(List<Integer> commits) {
        this.commits = commits;
    }
}
