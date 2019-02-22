package com.desperado.teamjob.service;

import com.desperado.teamjob.config.WebSecurityConfig;
import com.desperado.teamjob.dao.LoginRecordDao;
import com.desperado.teamjob.domain.LoginRecord;
import com.desperado.teamjob.utils.AddressUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service("loginRecordService")
public class LoginRecordServiceImpl implements LoginRecordService{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRecordServiceImpl.class);
    @Autowired
    private LoginRecordDao loginRecordDao;

    @Async
    @Override
    public void addRecord(String userName,String ipAddress,String browserType) {
        String address = null;
        try {
             address = AddressUtils.getAddresses(ipAddress,"utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUserName(userName);
        loginRecord.setIpAddress(ipAddress);
        loginRecord.setBrowserType(browserType);
        loginRecord.setLoginTime(new Date());
        loginRecord.setAddress(address);
        loginRecordDao.addRecord(loginRecord);
    }

    @Async
    @Override
    public void updateRecord(Date logoutTime, int id) {
        loginRecordDao.updateRecord(logoutTime,id);
    }
}
