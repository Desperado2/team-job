package com.desperado.teamjob.service;

import com.desperado.teamjob.domain.Weekly;
import com.desperado.teamjob.dto.WeeklyDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeeklyService {

    Weekly addWeek(WeeklyDto weekly);

    List<Weekly> getAllWeeklyByWeek(Integer week);

    Weekly getWeeklyById(String id);

    Weekly getWeeklyByIdAndWeek(String userId,Integer week);
}
