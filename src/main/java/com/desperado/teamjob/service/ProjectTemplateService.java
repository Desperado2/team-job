package com.desperado.teamjob.service;

import com.desperado.teamjob.domain.ProjectTemplate;
import com.desperado.teamjob.vo.Result;

public interface ProjectTemplateService {

    Result addOrUpdate(ProjectTemplate projectTemplate);

    Result getAllProjectDate();

    Result getProjectDateById(String id);

    Result getDetailById(String id);
}
