package com.desperado.teamjob.service;

import com.desperado.teamjob.dao.GitCommitLogDao;
import com.desperado.teamjob.dao.ProjectDao;
import com.desperado.teamjob.domain.GitCommitLogs;
import com.desperado.teamjob.domain.Project;
import com.desperado.teamjob.dto.GitCommitChart;
import com.desperado.teamjob.dto.GitCommitDto;
import com.desperado.teamjob.thread.GitLogService;
import com.desperado.teamjob.utils.DateUtil;
import com.desperado.teamjob.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

            Date projectDateCreate = project.getProjectDateCreate();
            //创建年
            int createYear = DateUtil.getYear(projectDateCreate);
            //本年
            int year = DateUtil.getYear(new Date());
            for (int begin = createYear; begin <= year; begin++){
                //创建年，从创建周到当年最后一周
                int weeks = DateUtil.getWeekOfYear(projectDateCreate);
                int maxWeeks = DateUtil.getMaxWeekNumOfYear(createYear);
                //如果不是本年也不是创建年，则从第一周到最后一周
                if(begin > createYear && begin < year){
                    weeks = 1;
                    maxWeeks = DateUtil.getMaxWeekNumOfYear(begin);
                }
                // 如果创建使其为本年， 则从本年第一周到现在
                if(begin == year){
                    weeks = 1;
                    maxWeeks = DateUtil.getWeekOfYear(new Date());
                }
                if(createYear == year){
                    weeks = DateUtil.getWeekOfYear(projectDateCreate);
                    maxWeeks = DateUtil.getWeekOfYear(new Date());
                }
                for (int i=weeks; i <= maxWeeks; i++){
                    Date firstDayOfWeek = DateUtil.getFirstDayOfWeek(begin, i);
                    Date lastDayOfWeek = DateUtil.getLastDayOfWeek(begin, i);
                    int submitDateFrom = (int) (firstDayOfWeek.getTime()/1000);
                    int submitDateTo = (int) (lastDayOfWeek.getTime()/1000);
                    List<GitCommitLogs> gitCommitLogs = gitLogService.listAllLinesByTime(url, name, submitDateFrom, submitDateTo);
                    if(gitCommitLogs != null && gitCommitLogs.size() > 0){
                        addLogs(gitCommitLogs);
                    }
                }
            }
        }
    }

    @Override
    public Result getWeeklyLogs(String yearweek) {
        Result result = new Result();
        List<GitCommitLogs> logsList = gitCommitLogDao.getWeeklyLogs(yearweek);
        Map<String, List<GitCommitLogs>> logsUserMap = logsUserMap(logsList);
        List<GitCommitDto> dealLogs = dealLogs(logsUserMap);
        GitCommitChart commitChart = getGitCommitChart(dealLogs);
        result.setData(commitChart);
        return result;
    }

    private Map<String,List<GitCommitLogs>> logsUserMap(List<GitCommitLogs> logsList){
        Map<String,List<GitCommitLogs>> map = new HashMap<>();
        for (GitCommitLogs logs : logsList){
            String  author= logs.getAuthor();
            if(map.containsKey(author)){
                List<GitCommitLogs> gitCommitLogs = map.get(author);
                gitCommitLogs.add(logs);
                map.put(author,gitCommitLogs);
            }else{
                List<GitCommitLogs> gitCommitLogs =new ArrayList<>();
                gitCommitLogs.add(logs);
                map.put(author,gitCommitLogs);
            }
        }
        return map;
    }

    private List<GitCommitDto> dealLogs(Map<String,List<GitCommitLogs>> maps){
        List<GitCommitDto> gitCommitDtoList = new ArrayList<>();
        GitCommitDto gitCommitDto;
        for (String author : maps.keySet()){
            gitCommitDto = new GitCommitDto();
            List<GitCommitLogs> gitCommitLogs = maps.get(author);
            int totalAddLines = 0;
            int totalDelLines = 0;
            int totalCommits = 0;
            for (GitCommitLogs logs : gitCommitLogs){
                totalAddLines += logs.getTotalAddLines();
                totalDelLines += logs.getTotalDelLines();
                totalCommits += 1;
            }
            gitCommitDto.setAuthorName(author);
            gitCommitDto.setTotalAddLines(totalAddLines);
            gitCommitDto.setTotalDelLines(totalDelLines);
            gitCommitDto.setTotalCommits(totalCommits);
            gitCommitDtoList.add(gitCommitDto);
        }
        return gitCommitDtoList;
    }

    private GitCommitChart getGitCommitChart(List<GitCommitDto> dealLogs){
        GitCommitChart gitCommitChart = new GitCommitChart();
        List<String> users = new ArrayList<>();
        List<Integer> addLines = new ArrayList<>();
        List<Integer> delLines = new ArrayList<>();
        List<Integer> commits = new ArrayList<>();
        for(GitCommitDto gitCommitDto : dealLogs){
            users.add(gitCommitDto.getAuthorName());
            addLines.add(gitCommitDto.getTotalAddLines());
            delLines.add(gitCommitDto.getTotalDelLines());
            commits.add(gitCommitDto.getTotalCommits());
        }
        gitCommitChart.setUsers(users);
        gitCommitChart.setAddLines(addLines);
        gitCommitChart.setDelLines(delLines);
        gitCommitChart.setCommits(commits);
        return gitCommitChart;
    }


    private void addLogs(List<GitCommitLogs> gitCommitLogs){
        List<GitCommitLogs> realLogs = new ArrayList<>();
        for (GitCommitLogs logs : gitCommitLogs){
            String commitId = logs.getCommitId();
            GitCommitLogs commitLogs = gitCommitLogDao.getLogsByCommitId(commitId);
            if(commitLogs == null){
                realLogs.add(logs);
            }
        }
        gitCommitLogDao.add(realLogs);
    }
}
