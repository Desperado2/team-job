package com.desperado.teamjob.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class ProjectTemplate {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("名称")
    private String projectName;
    @ApiModelProperty("项目经理")
    private String projectManger;
    @ApiModelProperty("项目级别")
    private String projectLevel;
    @ApiModelProperty("项目属性")
    private String projectProperty;
    @ApiModelProperty("项目需求文档地址")
    private String projectPrd;
    @ApiModelProperty("本项目组成员")
    private String groupMembers;
    @ApiModelProperty("服务端开发人员")
    private String projectServer;
    @ApiModelProperty("前端/客户端开发人员")
    private String projectFront;
    @ApiModelProperty("测试人员")
    private String projectTester;
    @ApiModelProperty("接口评审日期")
    private Date interfaceReview;
    @ApiModelProperty("用例评审日期")
    private Date caseReview;
    @ApiModelProperty("接口测试日期")
    private Date interfaceTest;
    @ApiModelProperty("整体测试日期")
    private Date allTest;
    @ApiModelProperty("预发日期")
    private Date preDate;
    @ApiModelProperty("上线日期")
    private Date productDate;
    @ApiModelProperty("项目描述")
    private String content;
    @ApiModelProperty("延期备注")
    private String remark;
    @ApiModelProperty("创建时间")
    private Date createDate;
    @ApiModelProperty("更新时间")
    private Date updateDate;
    @ApiModelProperty("创建人")
    private String owner;
    @ApiModelProperty("所属项目组")
    private String group;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectManger() {
        return projectManger;
    }

    public void setProjectManger(String projectManger) {
        this.projectManger = projectManger;
    }

    public String getProjectLevel() {
        return projectLevel;
    }

    public void setProjectLevel(String projectLevel) {
        this.projectLevel = projectLevel;
    }

    public String getProjectProperty() {
        return projectProperty;
    }

    public void setProjectProperty(String projectProperty) {
        this.projectProperty = projectProperty;
    }

    public String getProjectPrd() {
        return projectPrd;
    }

    public void setProjectPrd(String projectPrd) {
        this.projectPrd = projectPrd;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getProjectServer() {
        return projectServer;
    }

    public void setProjectServer(String projectServer) {
        this.projectServer = projectServer;
    }

    public String getProjectFront() {
        return projectFront;
    }

    public void setProjectFront(String projectFront) {
        this.projectFront = projectFront;
    }

    public String getProjectTester() {
        return projectTester;
    }

    public void setProjectTester(String projectTester) {
        this.projectTester = projectTester;
    }

    public Date getInterfaceReview() {
        return interfaceReview;
    }

    public void setInterfaceReview(Date interfaceReview) {
        this.interfaceReview = interfaceReview;
    }

    public Date getCaseReview() {
        return caseReview;
    }

    public void setCaseReview(Date caseReview) {
        this.caseReview = caseReview;
    }

    public Date getInterfaceTest() {
        return interfaceTest;
    }

    public void setInterfaceTest(Date interfaceTest) {
        this.interfaceTest = interfaceTest;
    }

    public Date getAllTest() {
        return allTest;
    }

    public void setAllTest(Date allTest) {
        this.allTest = allTest;
    }

    public Date getPreDate() {
        return preDate;
    }

    public void setPreDate(Date preDate) {
        this.preDate = preDate;
    }

    public Date getProduceDate() {
        return productDate;
    }

    public void setProduceDate(Date productDate) {
        this.productDate = productDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
