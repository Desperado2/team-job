package com.desperado.teamjob.dao;

import com.desperado.teamjob.domain.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectDao {

    void addProject(Project project);

    void update(Project project);

    List<Project> selectAllProject();

    Project selectProjectById(@Param("id") String id);

    void deleteProjectById(@Param("id") String id);

    List<Project> selectProjectByUserId(@Param("userId") String userId);

    List<Project> selectProjectByIds(List<String> ids);
}
