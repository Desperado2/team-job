package com.desperado.teamjob.json;

import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.desperado.teamjob.service.GitLogAnalysisService;
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

@RestController
@RequestMapping("/gitAnalysiss")
@Api(tags = "1.3", description = "代码统计管理", value = "代码统计管理")
public class GitAnalysiss {

    private static final Logger LOGGER = LoggerFactory.getLogger(Projects.class);

    @Autowired
    private GitLogAnalysisService gitLogAnalysisService;

    @GetMapping("/{yearweek}")
    @ApiOperation(value = "跟进周期查询全部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yearweek", value = "周数[20196格式]", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
    })
    public Result query(@PathVariable String yearweek) {
        Result result = gitLogAnalysisService.getWeeklyLogs(yearweek);
        return result;
    }


}
