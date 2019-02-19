package com.desperado.teamjob.service;

import com.desperado.teamjob.dao.UserDao;
import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.utils.PwdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserDto> addUser(User user) {
        user.setPassword(PwdUtil.encoder(user.getPassword()));
        userDao.addUser(user);
        return userDao.selectAllUser();
    }

    @Override
    public List<UserDto> selectAllUser() {
        List<UserDto> users = userDao.selectAllUser();
        return users;
    }

    @Override
    public UserDto selectUserById(String id) {
        UserDto user = userDao.selectUserById(id);
        return user;
    }
}
