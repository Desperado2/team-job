package com.desperado.teamjob.json;

import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.service.UserService;
import com.desperado.teamjob.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "1.0", description = "用户管理", value = "用户管理")
public class Users {
    private static final Logger LOGGER = LoggerFactory.getLogger(Users.class);
    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "添加用户")
    public List<UserDto> addUser(@RequestBody User user){
        return userService.addUser(user);
    }


    @PostMapping("/pic")
    @ApiOperation(value = "上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片", dataType = ApiDataType.BINARY, paramType = ApiParamType.BODY),
    })
    public String upload(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = "/Users/itinypocket/workspace/temp/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
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
