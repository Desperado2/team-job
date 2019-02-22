package com.desperado.teamjob.dao;

import com.desperado.teamjob.domain.LoginRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface LoginRecordDao {

    void addRecord(LoginRecord loginRecord);
    void updateRecord(@Param("logoutTime") Date logoutTime,@Param("id") int id);
}
