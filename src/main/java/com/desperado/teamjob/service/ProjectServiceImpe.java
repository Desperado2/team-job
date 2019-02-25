package com.desperado.teamjob.service;

import com.desperado.teamjob.dao.ProjectDao;
import com.desperado.teamjob.dao.ProjectUserDao;
import com.desperado.teamjob.dao.UserDao;
import com.desperado.teamjob.domain.Project;
import com.desperado.teamjob.domain.ProjectUser;
import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.dto.ProjectDto;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.utils.IdGenerator;
import com.desperado.teamjob.utils.UserUtils;
import com.desperado.teamjob.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("projectService")
public class ProjectServiceImpe implements ProjectService {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProjectUserDao projectUserDao;

    @Override
    public Result addOrUpdateUser(Project project) {
        Result result = new Result();
        try {
            if(!StringUtils.isEmpty(project.getId())){
                project.setDateUpdate(new Date());
                projectUserDao.deleteByProjectId(project.getId());
                insertProjectUser(project.getId(),project.getCoder());
                projectDao.update(project);
            }else{
                Date date = new Date();
                String id = getId();
                project.setId(id);
                project.setDateCreate(date);
                project.setDateUpdate(date);
                project.setOptioner(UserUtils.getUser().getId());
                insertProjectUser(id,project.getCoder());
                projectDao.addProject(project);
            }
            result.setData(project);
        }catch (Exception e){
            result.setMsg("创建应用失败");
            result.setSuccess(false);
        }
        return result;
    }


    @Override
    public Result selectAllProject() {
        Result result = new Result();
        List<Project> projects = projectDao.selectAllProject();
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projects){
            ProjectDto projectDto = new ProjectDto();
            BeanUtils.copyProperties(project,projectDto);
            projectDto.setCoders(queryUsers(project.getCoder()));
            projectDtos.add(projectDto);
        }
        result.setData(projectDtos);
        return result;
    }

    @Override
    public Result selectProjectById(String id) {
        Result result = new Result();
        Project projects = projectDao.selectProjectById(id);
        ProjectDto projectDto = new ProjectDto();
        BeanUtils.copyProperties(projects,projectDto);
        projectDto.setCoders(queryUsers(projects.getCoder()));
        result.setData(projectDto);
        return result;
    }

    @Override
    public Result selectProjectByUserId(String userId) {
        Result result = new Result();
        List<String> projectIds = getProjectIds(userId);
        if(projectIds == null || projectIds.size() == 0){
            result.setMsg("没有维护的项目");
            result.setSuccess(false);
            return result;
        }
        List<Project> projects = projectDao.selectProjectByIds(projectIds);
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projects){
            ProjectDto projectDto = new ProjectDto();
            BeanUtils.copyProperties(project,projectDto);
            projectDto.setCoders(queryUsers(project.getCoder()));
            projectDtos.add(projectDto);
        }
        result.setData(projectDtos);
        return result;
    }

    @Override
    public Result deleteProjectById(String id) {
        Result result = new Result();
        Project project = projectDao.selectProjectById(id);
        if(project == null){
            result.setSuccess(false);
            result.setMsg("应用不存在");
            return result;
        }
        if(!UserUtils.getUser().getId().equals(project.getOptioner())){
            result.setSuccess(false);
            result.setMsg("只有创建者才可以删除");
            return result;
        }
        projectUserDao.deleteByProjectId(id);
        projectDao.deleteProjectById(id);
        return null;
    }

    /**
     * 获取id
     * @return
     */
    private String getId(){
        String id = IdGenerator.generate(8);
        Project project = projectDao.selectProjectById(id);
        while (project != null){
            id = IdGenerator.generate(8);
            project = projectDao.selectProjectById(id);
        }
        return id;
    }

    private List<UserDto> queryUsers(String users){
        if(StringUtils.isEmpty(users)){
            return new ArrayList<>();
        }
        String[] ids = users.split(",");
        return userDao.selectUserByIds(Arrays.asList(ids));
    }

    private List<String> getProjectIds(String userId){
        List<ProjectUser> projectUsers = projectUserDao.findByUserId(userId);
        List<String> projectIds = new ArrayList<>();
        for (ProjectUser projectUser : projectUsers){
            projectIds.add(projectUser.getProjectId());
        }
        return projectIds;
    }

    private void insertProjectUser(String projectId,String coders){
        String[] userIds = coders.split(",");
        List<ProjectUser> projectUsers = new ArrayList<>();
        ProjectUser projectUser = null;
        for (String userId : userIds){
            projectUser = new ProjectUser();
            projectUser.setProjectId(projectId);
            projectUser.setUserId(userId);
            projectUsers.add(projectUser);
        }
        projectUserDao.add(projectUsers);
    }
}
