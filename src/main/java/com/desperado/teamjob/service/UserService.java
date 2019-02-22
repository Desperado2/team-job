package com.desperado.teamjob.service;

import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.vo.Result;

import java.util.List;

public interface UserService {
    Result addOrUpdateUser(User user);

    Result selectAllUser();

    Result updatePassword(User user,String oldPassword,String newPassword);

    Result selectUserById(String id);
}
