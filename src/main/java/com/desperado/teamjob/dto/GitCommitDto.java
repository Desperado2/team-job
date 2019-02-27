package com.desperado.teamjob.dto;

public class GitCommitDto {
    private String author;
    private String authorName;
    private Integer totalAddLines;
    private Integer totalDelLines;
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
