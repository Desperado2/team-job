package com.desperado.teamjob.dto;

import com.desperado.teamjob.domain.User;
import com.desperado.teamjob.enums.RepositoryType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel
public class ProjectDto implements Serializable {
    @ApiModelProperty("项目id")
    private String id;
    @ApiModelProperty("项目中文名")
    private String projectRealName;
    @ApiModelProperty("项目名")
    private String projectName;
    @ApiModelProperty("项目仓库地址")
    private String repositoryUrl;
    @ApiModelProperty("项目仓库类型名称")
    private String repositoryTypeName;
    @ApiModelProperty("项目仓库类型")
    private Integer repositoryType;
    @ApiModelProperty("项目文档地址")
    private String documentUrl;
    @ApiModelProperty("线上数据库地址")
    private String databaseUrl;
    @ApiModelProperty("开发者")
    private List<UserDto> coderList;
    @ApiModelProperty("创建者")
    private String optioner;
    @ApiModelProperty("项目创建时间")
    private Date projectDateCreate;
    @ApiModelProperty("创建时间")
    private Date dateCreate;
    @ApiModelProperty("更新时间")
    private Date dateUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectRealName() {
        return projectRealName;
    }

    public void setProjectRealName(String projectRealName) {
        this.projectRealName = projectRealName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getRepositoryType() {
        return repositoryType;
    }

    public void setRepositoryType(Integer repositoryType) {
        this.repositoryType = repositoryType;
    }
    public String getRepositoryTypeName() {
        if(repositoryType == RepositoryType.SVN.getCode()){
            return "Svn";
        }else if(repositoryType == RepositoryType.GIT.getCode()){
            return "Git";
        }
        return "unKnow";
    }

    public void setRepositoryTypeName(String repositoryTypeName) {
        this.repositoryTypeName = repositoryTypeName;
    }
    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public List<UserDto> getCoderList() {
        return coderList;
    }

    public void setCoderList(List<UserDto> coderList) {
        this.coderList = coderList;
    }

    public Date getProjectDateCreate() {
        return projectDateCreate;
    }

    public void setProjectDateCreate(Date projectDateCreate) {
        this.projectDateCreate = projectDateCreate;
    }

    public String getOptioner() {
        return optioner;
    }

    public void setOptioner(String optioner) {
        this.optioner = optioner;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
