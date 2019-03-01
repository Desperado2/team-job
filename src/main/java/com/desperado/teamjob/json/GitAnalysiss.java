package com.desperado.teamjob.json;

import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.desperado.teamjob.domain.Project;
import com.desperado.teamjob.service.GitLogAnalysisService;
import com.desperado.teamjob.thread.SvnLogService;
import com.desperado.teamjob.utils.DateUtil;
import com.desperado.teamjob.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/gitAnalysiss")
@Api(tags = "1.3", description = "代码统计管理", value = "代码统计管理")
public class GitAnalysiss {

    private static final Logger LOGGER = LoggerFactory.getLogger(Projects.class);

    @Autowired
    private GitLogAnalysisService gitLogAnalysisService;
    @Autowired
    private SvnLogService svnLogService;

    @GetMapping("/{yearweek}")
    @ApiOperation(value = "跟进周期查询全部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yearweek", value = "周数[20196格式]", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
    })
    public Result query(@PathVariable String yearweek) {
        Result result = gitLogAnalysisService.getWeeklyLogs(yearweek);
        return result;
    }

    @GetMapping("/{projectCode}/project")
    @ApiOperation(value = "跟进周期查询全部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectCode", value = "项目code", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
    })
    public Result getProject(@PathVariable String projectCode) {
        Result result = gitLogAnalysisService.getProjectLogs(projectCode);
        return result;
    }

    @GetMapping("/test")
    @ApiOperation(value = "测试")
    public Result test() {
        Project project = new Project();
        project.setProjectName("test-demo");
        project.setProjectRealName("测试svn");
        project.setRepositoryUrl("https://118.24.115.208:443/svn/test_demo");
        project.setDateCreate(new Date());
        int weeks = DateUtil.getWeekOfYear(project.getDateCreate());
        int maxWeeks = DateUtil.getWeekOfYear(new Date());
        Date firstDayOfWeek = DateUtil.getFirstDayOfWeek(2019, weeks);
        Date lastDayOfWeek = DateUtil.getLastDayOfWeek(2019, maxWeeks);
        svnLogService.listAllLinesByTime(project,firstDayOfWeek,lastDayOfWeek);
        return null;
    }

}
