package com.desperado.teamjob.service;

import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    Result addOrUpdateUser(HttpServletRequest request,User user);

    Result selectAllUser(boolean isSplit);

    Result updatePassword(User user,String oldPassword,String newPassword);

    Result selectUserById(String id);
}
