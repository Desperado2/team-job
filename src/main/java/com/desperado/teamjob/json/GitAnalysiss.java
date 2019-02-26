package com.desperado.teamjob.json;

import com.desperado.teamjob.service.GitLogAnalysisService;
import com.desperado.teamjob.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping
    @ApiOperation(value = "查询全部")
    public Result query() {
        gitLogAnalysisService.saveOrUpdate();
        return null;
    }


}
