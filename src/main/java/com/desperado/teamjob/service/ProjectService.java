package com.desperado.teamjob.service;

import com.desperado.teamjob.domain.Project;
import com.desperado.teamjob.vo.Result;

public interface ProjectService {

    Result addOrUpdateUser(Project project);


    Result selectAllProject();

    Result selectProjectById(String id);

    Result selectProjectByUserId(String userId);

    Result deleteProjectById(String id);
}
