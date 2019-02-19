package com.desperado.teamjob.dao;

import com.desperado.teamjob.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {

    User selectUserByEmail(@Param("email") String email);
}
