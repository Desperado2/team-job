package com.desperado.teamjob.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class GitCommitDto {
    @ApiModelProperty("用户名")
    private String author;
    @ApiModelProperty("用户真实名称")
    private String authorName;
    @ApiModelProperty("添加总行数")
    private Integer totalAddLines;
    @ApiModelProperty("删除总行数")
    private Integer totalDelLines;
    @ApiModelProperty("提交总次数")
    private Integer totalCommits;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getTotalAddLines() {
        return totalAddLines;
    }

    public void setTotalAddLines(Integer totalAddLines) {
        this.totalAddLines = totalAddLines;
    }

    public Integer getTotalDelLines() {
        return totalDelLines;
    }

    public void setTotalDelLines(Integer totalDelLines) {
        this.totalDelLines = totalDelLines;
    }

    public Integer getTotalCommits() {
        return totalCommits;
    }

    public void setTotalCommits(Integer totalCommits) {
        this.totalCommits = totalCommits;
    }
}
