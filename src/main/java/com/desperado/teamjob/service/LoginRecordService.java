package com.desperado.teamjob.service;

import com.desperado.teamjob.domain.LoginRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface LoginRecordService {
    void addRecord(String userName,String ipAddress,String browserType);
    void updateRecord(Date logoutTime,int id);
}
