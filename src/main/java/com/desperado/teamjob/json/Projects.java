package com.desperado.teamjob.json;

import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.desperado.teamjob.config.UploadConfig;
import com.desperado.teamjob.domain.Project;
import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.service.ProjectService;
import com.desperado.teamjob.service.UserService;
import com.desperado.teamjob.utils.FileUtils;
import com.desperado.teamjob.utils.PwdUtil;
import com.desperado.teamjob.utils.UserUtils;
import com.desperado.teamjob.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@Api(tags = "1.2", description = "应用管理", value = "应用管理")
public class Projects {
    private static final Logger LOGGER = LoggerFactory.getLogger(Projects.class);

    @Autowired
    private ProjectService projectService;

    @PostMapping
    @ApiOperation(value = "添加/编辑用户")
    public Result addOrUpdateProject(@RequestBody Project project){
        return projectService.addOrUpdateProject(project);
    }

    @GetMapping
    @ApiOperation(value = "查询全部")
    public Result query() {
        return projectService.selectAllProject();
    }

    @GetMapping("/user")
    @ApiOperation(value = "查询登录用户的全部")
    public Result queryByUser() {
        User user = UserUtils.getUser();
        return projectService.selectProjectByUserId(user.getId());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目id", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
    })
    public Result get(@PathVariable String id) {
        return projectService.selectProjectById(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据主键删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目id", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
    })
    public Result delete(@PathVariable String id) {
        return projectService.deleteProjectById(id);
    }

}
