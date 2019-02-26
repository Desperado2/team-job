/**
 * 
 */
package com.desperado.teamjob.domain;

import java.util.Date;


public class GitCommit {
	private String id;
	private String projectId;
	private String gitObjectId;
	private String gitDiffFile;
	private String contributor;
	private String lastCommitUrl;
	private Date contributeDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getGitObjectId() {
		return gitObjectId;
	}
	public void setGitObjectId(String gitObjectId) {
		this.gitObjectId = gitObjectId;
	}
	public String getGitDiffFile() {
		return gitDiffFile;
	}
	public void setGitDiffFile(String gitDiffFile) {
		this.gitDiffFile = gitDiffFile;
	}
	public String getContributor() {
		return contributor;
	}
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	public String getLastCommitUrl() {
		return lastCommitUrl;
	}
	public void setLastCommitUrl(String lastCommitUrl) {
		this.lastCommitUrl = lastCommitUrl;
	}
	public Date getContributeDate() {
		return contributeDate;
	}
	public void setContributeDate(Date contributeDate) {
		this.contributeDate = contributeDate;
	}
}
