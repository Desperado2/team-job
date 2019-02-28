package com.desperado.teamjob.service;

import com.desperado.teamjob.dto.WeeklyDto;
import com.desperado.teamjob.vo.Result;

import java.util.List;

public interface WeeklyService {

    Result addWeek(WeeklyDto weekly);

    Result getAllWeeklyByWeek(Integer week);

    Result getWeeklyById(String id);

    Result getWeeklyByIdAndWeek(String userId,Integer week);

    Result getWeeklyReportCommitData();
}
