package com.desperado.teamjob.service;

import com.desperado.teamjob.domain.Weekly;
import com.desperado.teamjob.dto.WeeklyDto;
import com.desperado.teamjob.vo.Result;

import java.util.List;

public interface WeeklyService {

    Result addOrUpdateWeeklyReport(Weekly weekly);

    Result getAllWeeklyByWeek(String week);

    Result getWeeklyById(String id);

    Result getWeeklyByIdAndWeek(String userId,String week);

    Result getWeeklyReportCommitData();

    Result getWeeklyByWeek(String week);
}
