package com.desperado.teamjob.domain;

import java.io.Serializable;
import java.util.Date;

public class GitCommitLogs implements Serializable {
	private String id;
	private String project;
	private String projectName;
	private String author;
	private String yearweek;
	private Integer totalAddLines;
	private Integer totalDelLines;
	private String commitId;
	private Date dateCommit;
	private String commitComment;
	private int commitType;
	private String group;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getYearweek() {
		return yearweek;
	}
	public void setYearweek(String yearweek) {
		this.yearweek = yearweek;
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
	public String getCommitId() {
		return commitId;
	}
	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}
	public Date getDateCommit() {
		return dateCommit;
	}
	public void setDateCommit(Date dateCommit) {
		this.dateCommit = dateCommit;
	}
	public String getCommitComment() {
		return commitComment;
	}
	public void setCommitComment(String commitComment) {
		this.commitComment = commitComment;
	}
	public int getCommitType() {
		return commitType;
	}
	public void setCommitType(int commitType) {
		this.commitType = commitType;
	}
}
