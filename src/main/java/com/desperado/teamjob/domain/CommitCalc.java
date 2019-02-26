
package com.desperado.teamjob.domain;

public class CommitCalc {
	private String author;
	private String authorEmail;
	private Integer totalAddLines;
	private Integer totalDelLines;
	private Integer totalCommits;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorEmail() {
		return authorEmail;
	}
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
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
