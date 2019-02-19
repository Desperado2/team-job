package com.desperado.teamjob.service;

import com.desperado.teamjob.dao.WeeklyDao;
import com.desperado.teamjob.domain.Weekly;
import com.desperado.teamjob.dto.WeeklyDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("weeklyService")
public class WeeklyServiceImpl implements WeeklyService {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private WeeklyDao weeklyDao;

    @Override
    public Weekly addWeek(WeeklyDto weeklyDto) {
        Weekly weekly = new Weekly();
        String id = UUID.randomUUID().toString();
        Date date = new Date();
        weekly.setId(id);
        weekly.setDateCreate(date);
        weekly.setDateUpdate(date);
        BeanUtils.copyProperties(weeklyDto,weekly);
        weeklyDao.addWeek(weekly);
        return weeklyDao.getWeeklyById(id);
    }

    @Override
    public List<Weekly> getAllWeeklyByWeek(Integer week) {

        return weeklyDao.getAllWeeklyByWeek(week);
    }

    @Override
    public Weekly getWeeklyById(String id) {
        return weeklyDao.getWeeklyById(id);
    }

    @Override
    public Weekly getWeeklyByIdAndWeek(String userId, Integer week) {
        return weeklyDao.getWeeklyByIdAndWeek(userId,week);
    }
}
