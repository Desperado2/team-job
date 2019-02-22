package com.desperado.teamjob.json;

import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.desperado.teamjob.config.UploadConfig;
import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.service.UserService;
import com.desperado.teamjob.service.UserServiceImpl;
import com.desperado.teamjob.utils.FileUtils;
import com.desperado.teamjob.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Api(tags = "1.0", description = "用户管理", value = "用户管理")
public class Users {
    private static final Logger LOGGER = LoggerFactory.getLogger(Users.class);
    @Autowired
    private UserService userService;

    @Autowired
    UploadConfig uploadConfig;

    @PostMapping
    @ApiOperation(value = "添加/编辑用户")
    public Result addOrUpdateUser(@RequestBody User user){
        return userService.addOrUpdateUser(user);
    }

    @PostMapping("/editPwd")
    @ApiOperation(value = "修改用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "原密码", dataType = ApiDataType.STRING, paramType = ApiParamType.BODY),
            @ApiImplicitParam(name = "newPassword", value = "新密码", dataType = ApiDataType.STRING, paramType = ApiParamType.BODY),
    })
    public Result editPwd(@RequestBody Map map){
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userService.updatePassword(user,map.get("oldPassword").toString(),map.get("newPassword").toString());
    }


    @PostMapping("/pic")
    @ApiOperation(value = "上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片", dataType = ApiDataType.BYTE, paramType = ApiParamType.BODY),
    })
    public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+fileType;
        String filePath = uploadConfig.getBasePath()+uploadConfig.getImagePath();
        try {
            FileUtils.uploadFile(file,filePath,fileName);
            LOGGER.info("上传成功");
            return "image/"+fileName;
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
        }
        return "fail";
    }

    @GetMapping
    @ApiOperation(value = "查询全部")
    public Result query() {
        return userService.selectAllUser();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = ApiDataType.STRING, paramType = ApiParamType.PATH),
    })
    public Result get(@PathVariable String id) {
       return userService.selectUserById(id);
    }


    @GetMapping("/user")
    @ApiOperation(value = "获取登录用户")
    public Result getUser() {
        User userDetails = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Result result = new Result();
        Result user = userService.selectUserById(userDetails.getId());
        result.setData(user.getData());
        return result;
    }

}
