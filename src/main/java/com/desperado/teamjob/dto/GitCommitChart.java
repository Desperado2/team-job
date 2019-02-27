package com.desperado.teamjob.dto;

import java.util.List;

public class GitCommitChart {
    List<String> users;
    List<Integer> addLines;
    List<Integer> delLines;
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
