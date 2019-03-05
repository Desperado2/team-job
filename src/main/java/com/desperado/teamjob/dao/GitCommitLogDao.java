package com.desperado.teamjob.dao;

import com.desperado.teamjob.domain.GitCommitLogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GitCommitLogDao {
    void add(List<GitCommitLogs> gitCommitLogs);

    List<GitCommitLogs> getWeeklyLogs(String yearweek);

    List<GitCommitLogs> getWeeklyLogsByAuthor(String username,String yearweek);

    GitCommitLogs getLogsByCommitId(String commitId);

    GitCommitLogs getProjectNewestLog(String project);

    List<GitCommitLogs> getLogByProjectCode(String project);
}
