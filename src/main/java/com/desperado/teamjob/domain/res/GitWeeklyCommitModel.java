/**
 * 
 */
package com.desperado.teamjob.domain.res;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


public class GitWeeklyCommitModel {
	@ApiModelProperty("提交信息")
	private String commitComment;
	@ApiModelProperty("提交日期")
	private Date commitDate;
	@ApiModelProperty("提交id")
	private String commitId;
	public String getCommitComment() {
		return commitComment;
	}
	public void setCommitComment(String commitComment) {
		this.commitComment = commitComment;
	}
	public Date getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	public String getCommitId() {
		return commitId;
	}
	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}
}
