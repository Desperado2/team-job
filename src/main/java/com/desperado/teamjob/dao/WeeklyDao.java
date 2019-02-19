package com.desperado.teamjob.dao;

import com.desperado.teamjob.domain.Weekly;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WeeklyDao {

    void addWeek(Weekly weekly);

    List<Weekly> getAllWeeklyByWeek(@Param("week") Integer week);

    Weekly getWeeklyById(@Param("id") String id);

    Weekly getWeeklyByIdAndWeek(@Param("userId") String userId,@Param("week") Integer week);
}
