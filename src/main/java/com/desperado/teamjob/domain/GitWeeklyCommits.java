
package com.desperado.teamjob.domain;

import java.io.Serializable;
import java.util.Date;


public class GitWeeklyCommits implements Serializable {
	private static final long serialVersionUID = 7440954765080019569L;
	private String id;
	private String project;
	private String author;
	private String yearweek;
	private Integer totalAddLines;
	private Integer totalDelLines;
	private Integer totalCommits;
	private Date dateCreate;

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

	public Integer getTotalCommits() {
		return totalCommits;
	}

	public void setTotalCommits(Integer totalCommits) {
		this.totalCommits = totalCommits;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}
}
