package com.desperado.teamjob.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class UserDto {
    @ApiModelProperty("用户id")
   private String id;
    @ApiModelProperty("用户名")
   private String name;
    @ApiModelProperty("用户名")
   private String email;
    @ApiModelProperty("用户邮箱")
   private String phone ;
    @ApiModelProperty("用户生日")
   private Date birthday;
    @ApiModelProperty("用户生日类型")
   private String birthType;
    @ApiModelProperty("用户部门")
   private String department;
    @ApiModelProperty("用户职务")
   private String position;
    @ApiModelProperty("用户头像路径")
   private String headUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirthType() {
        return birthType;
    }

    public void setBirthType(String birthType) {
        this.birthType = birthType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
