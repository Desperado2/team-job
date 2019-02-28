package com.desperado.teamjob.dao;

import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserDao {

    void addUser(User user);

    void update(User user);

    void updatePassword(User user);

    List<UserDto> selectAllUser();

    UserDto selectUserById(@Param("id") String id);

    UserDto selectUserByRepositoryUsername( String repositoryUsername);

    UserDto selectUserByEmail( String email);

    List<UserDto> selectUserByIds(@Param("ids") List<String> ids);

    List<UserDto> selectUserWithoutIds(@Param("ids") List<String> ids);
}
