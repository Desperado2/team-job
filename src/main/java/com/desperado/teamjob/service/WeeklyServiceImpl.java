package com.desperado.teamjob.service;

import com.desperado.teamjob.dao.UserDao;
import com.desperado.teamjob.dao.WeeklyDao;
import com.desperado.teamjob.domain.Weekly;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.dto.WeeklyDto;
import com.desperado.teamjob.dto.WeeklyReportDto;
import com.desperado.teamjob.utils.DateUtil;
import com.desperado.teamjob.utils.StringUtil;
import com.desperado.teamjob.utils.UserUtils;
import com.desperado.teamjob.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public Result addOrUpdateWeeklyReport(Weekly weekly) {
        Result result = new Result();
       if(StringUtil.isNotEmpty(weekly.getId())){
           Date date = new Date();
           weekly.setDateUpdate(date);
           weeklyDao.updateWeek(weekly);
       }else{
           String id = UUID.randomUUID().toString();
           String userId = UserUtils.getUser().getId();
           Date date = new Date();
           String week = DateUtil.getYearWeek(date);
           weekly.setId(id);
           weekly.setUserId(userId);
           weekly.setWeek(week);
           weekly.setDateCreate(date);
           weekly.setDateUpdate(date);
           weeklyDao.addWeek(weekly);
       }
        result.setData(weekly);
        return result;
    }

    @Override
    public Result getAllWeeklyByWeek(String week) {
        Result result = new Result();
        List<Weekly> weeklys = weeklyDao.getAllWeeklyByWeek(week);
        List<WeeklyDto> weeklyDtos = dealWeekly(weeklys);
        result.setData(weeklyDtos);
        return result;

    }

    @Override
    public Result getWeeklyById(String id) {
        Result result = new Result();
        result.setData(weeklyDao.getWeeklyById(id));
        return result;
    }

    @Override
    public Result getWeeklyByIdAndWeek(String userId, String week) {
        Result result = new Result();
        result.setData(weeklyDao.getWeeklyByIdAndWeek(userId,week));
        return result;
    }

    @Override
    public Result getWeeklyReportCommitData() {

        WeeklyReportDto weeklyReportDto = new WeeklyReportDto();
        String week = DateUtil.getYearWeek(new Date());
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

    @Override
    public Result getWeeklyByWeek(String week) {
        String userId = UserUtils.getUser().getId();
        Weekly weekly = weeklyDao.getWeeklyByIdAndWeek(userId, week);
        Result result = new Result();
        result.setData(weekly);
        return result;
    }


    private List<WeeklyDto> dealWeekly(List<Weekly> weeklys){
        List<WeeklyDto> list = new ArrayList<>();
        WeeklyDto weeklyDto = null;
        for (Weekly weekly : weeklys){
            weeklyDto = new WeeklyDto();
            weeklyDto.setThisWeekReport(weekly.getThisWeekReport().replace("\n","<br><br>"));
            weeklyDto.setNextWeekReport(weekly.getNextWeekReport().replace("\n","<br><br>"));
            weeklyDto.setFeeling(weekly.getFeeling().replace("\n","<br>"));
            UserDto userDto = userDao.selectUserById(weekly.getUserId());
            weeklyDto.setUsername(userDto.getName());
            weeklyDto.setHeadurl(userDto.getHeadUrl());

            list.add(weeklyDto);
        }
        return list;
    }
}
