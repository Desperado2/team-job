package com.desperado.teamjob.json;

import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.domain.Weekly;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.dto.WeeklyDto;
import com.desperado.teamjob.service.UserService;
import com.desperado.teamjob.service.WeeklyService;
import com.desperado.teamjob.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weeklys")
@Api(tags = "1.1", description = "周报管理", value = "周报管理")
public class Weeklys {

    @Autowired
    private WeeklyService weeklyService;

    @PostMapping
    @ApiOperation(value = "添加周报")
    public Result addUser(@RequestBody WeeklyDto weekly){
        return weeklyService.addWeek(weekly);
    }

    @GetMapping("/{week}/all")
    @ApiOperation(value = "根据周数查询全部")
    public Result query(@PathVariable Integer week) {
        return weeklyService.getAllWeeklyByWeek(week);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
    })
    public Result get(@PathVariable String id) {
       return weeklyService.getWeeklyById(id);
    }


    @GetMapping("/commitData")
    @ApiOperation(value = "查询周报提交情况")
    public Result get() {
        return weeklyService.getWeeklyReportCommitData();
    }

    @GetMapping("/{userId}/{week}")
    @ApiOperation(value = "用户id和周数查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
            @ApiImplicitParam(name = "week", value = "周数", dataType = ApiDataType.INT, paramType = ApiParamType.PATH),
    })
    public Result get(@PathVariable String userId,@PathVariable Integer week) {
        return weeklyService.getWeeklyByIdAndWeek(userId,week);
    }

}
