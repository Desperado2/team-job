package com.desperado.teamjob.dao;

import com.desperado.teamjob.domain.Weekly;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WeeklyDao {

    void addWeek(Weekly weekly);

    void updateWeek(Weekly weekly);

    List<Weekly> getAllWeeklyByWeek(@Param("week") String week);

    List<String> getAllUserIdsByWeek(@Param("week") String week);

    Weekly getWeeklyById(@Param("id") String id);

    Weekly getWeeklyByIdAndWeek(@Param("userId") String userId,@Param("week") String week);
}
