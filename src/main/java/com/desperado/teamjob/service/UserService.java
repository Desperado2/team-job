package com.desperado.teamjob.service;

import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> addUser(User user);

    List<UserDto> selectAllUser();

    UserDto selectUserById(String id);
}
