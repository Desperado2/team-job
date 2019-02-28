package com.desperado.teamjob.service;

import com.desperado.teamjob.vo.Result;

public interface GitLogAnalysisService {

    void saveOrUpdate();

     Result getWeeklyLogs(String yearweek);

     Result getProjectLogs(String project);
}
