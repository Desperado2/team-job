package com.desperado.teamjob.service;

import com.desperado.teamjob.dao.GitCommitLogDao;
import com.desperado.teamjob.dao.ProjectDao;
import com.desperado.teamjob.domain.GitCommitLogs;
import com.desperado.teamjob.domain.Project;
import com.desperado.teamjob.thread.GitLogService;
import com.desperado.teamjob.utils.DateUtil;
import com.desperado.teamjob.vo.Result;
import org.eclipse.jgit.lib.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("gitLogAnalysisService")
public class GitLogAnalysisServiceImpl implements GitLogAnalysisService {

    @Autowired
    private GitLogService gitLogService;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private GitCommitLogDao gitCommitLogDao;

    @Override
    public void saveOrUpdate() {
        List<Project> projects = projectDao.selectAllProject();
        for (Project project : projects){
            String url = project.getRepositoryUrl();
            String name = project.getProjectName();
            Repository repository = gitLogService.getRepositoryInfo(url);
            int weeks = DateUtil.getMaxWeekNumOfYear(2018);
            for (int i=1; i<= weeks; i++){
                Date firstDayOfWeek = DateUtil.getFirstDayOfWeek(2018, i);
                Date lastDayOfWeek = DateUtil.getLastDayOfWeek(2018, i);
                int submitDateFrom = (int) (firstDayOfWeek.getTime()/1000);
                int submitDateTo = (int) (lastDayOfWeek.getTime()/1000);
                List<GitCommitLogs> gitCommitLogs = gitLogService.listAllLinesByTime(url, name, submitDateFrom, submitDateTo);
                if(gitCommitLogs != null && gitCommitLogs.size() > 0){
                     gitCommitLogDao.add(gitCommitLogs);
                }
            }
        }
    }

    @Override
    public Result getAll() {
        List<Project> projects = projectDao.selectAllProject();
        for (Project project : projects){
            String url = project.getRepositoryUrl();
            String name = project.getProjectName();
            //gitLogService.l
        }
        return null;
    }
}
