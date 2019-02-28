package com.desperado.teamjob.service;

import com.desperado.teamjob.dao.UserDao;
import com.desperado.teamjob.dao.WeeklyDao;
import com.desperado.teamjob.domain.Weekly;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.dto.WeeklyDto;
import com.desperado.teamjob.dto.WeeklyReportDto;
import com.desperado.teamjob.utils.DateUtil;
import com.desperado.teamjob.vo.Result;
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
    @Autowired
    private UserDao userDao;

    @Override
    public Result addWeek(WeeklyDto weeklyDto) {
        Weekly weekly = new Weekly();
        String id = UUID.randomUUID().toString();
        Date date = new Date();
        weekly.setId(id);
        weekly.setDateCreate(date);
        weekly.setDateUpdate(date);
        BeanUtils.copyProperties(weeklyDto,weekly);
        weeklyDao.addWeek(weekly);
        Result result = new Result();
        result.setData( weeklyDao.getWeeklyById(id));
        return result;
    }

    @Override
    public Result getAllWeeklyByWeek(Integer week) {
        Result result = new Result();
        result.setData(weeklyDao.getAllWeeklyByWeek(week));
        return result;

    }

    @Override
    public Result getWeeklyById(String id) {
        Result result = new Result();
        result.setData(weeklyDao.getWeeklyById(id));
        return result;
    }

    @Override
    public Result getWeeklyByIdAndWeek(String userId, Integer week) {
        Result result = new Result();
        result.setData(weeklyDao.getWeeklyByIdAndWeek(userId,week));
        return result;
    }

    @Override
    public Result getWeeklyReportCommitData() {
        WeeklyReportDto weeklyReportDto = new WeeklyReportDto();
        int week = DateUtil.getWeekOfYear(new Date());
        List<String> weeks = weeklyDao.getAllUserIdsByWeek(week);
        if(weeks != null && weeks.size() > 0){
            List<UserDto> userDtos = userDao.selectUserWithoutIds(weeks);
            weeklyReportDto.setCommitCount(weeks.size());
            weeklyReportDto.setNotCommitUsers(userDtos);
            weeklyReportDto.setUserCount(weeks.size() + userDtos.size());
        }else{
            List<UserDto> userDtos = userDao.selectAllUser();
            weeklyReportDto.setCommitCount(0);
            weeklyReportDto.setNotCommitUsers(userDtos);
            weeklyReportDto.setUserCount(userDtos.size());
        }
        Result result = new Result();
        result.setData(weeklyReportDto);
        return result;
    }
}
