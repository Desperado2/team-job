package com.desperado.teamjob.dao;

import com.desperado.teamjob.domain.ProjectTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectTemplateDao {

     void  add(ProjectTemplate projectTemplate);

     void update(ProjectTemplate projectTemplate);

     List<ProjectTemplate> getAllProjectDate();

     ProjectTemplate getProjectDateById(String id);

}
