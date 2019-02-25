package com.desperado.teamjob.dao;

import com.desperado.teamjob.domain.ProjectUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectUserDao {
    void add(List<ProjectUser> projectUser);

    void deleteByProjectId(String projectId);

    List<ProjectUser> findByUserId(String userId);

    List<ProjectUser> findByProjectId(String projectId);

}
