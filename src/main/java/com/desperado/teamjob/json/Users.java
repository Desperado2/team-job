package com.desperado.teamjob.json;

import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "1.0", description = "用户管理", value = "用户管理")
public class Users {

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "添加用户")
    public List<UserDto> addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping
    @ApiOperation(value = "查询全部")
    public List<UserDto> query() {
        return userService.selectAllUser();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
    })
    public UserDto get(@PathVariable String id) {
       return userService.selectUserById(id);
    }

}
