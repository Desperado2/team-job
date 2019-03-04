package com.desperado.teamjob.json;

import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.desperado.teamjob.domain.ProjectTemplate;
import com.desperado.teamjob.service.ProjectTemplateService;
import com.desperado.teamjob.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("projectTemplates")
@Api(tags = "1.4", description = "项目排期管理", value = "项目排期管理")
public class ProjectTemplates {
    private static final Logger LOGGER = LoggerFactory.getLogger(Projects.class);

    @Autowired
    private ProjectTemplateService projectTemplateService;

    @PostMapping
    @ApiOperation(value = "添加/编辑项目排期")
    public Result addOrUpdate(@RequestBody ProjectTemplate template){
        return projectTemplateService.addOrUpdate(template);
    }

    @GetMapping
    @ApiOperation(value = "查询全部")
    public Result query() {
        return projectTemplateService.getAllProjectDate();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
    })
    public Result get(@PathVariable String id) {
        return projectTemplateService.getProjectDateById(id);
    }


    @GetMapping("/{id}/details")
    @ApiOperation(value = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
    })
    public Result getDetails(@PathVariable String id) {
        return projectTemplateService.getDetailById(id);
    }
}
