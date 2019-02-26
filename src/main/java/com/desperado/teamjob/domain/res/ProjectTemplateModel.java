package com.desperado.teamjob.domain.res;



import com.desperado.teamjob.dto.UserDto;

import java.util.List;


public class ProjectTemplateModel {

    private String id;

    private String storyName;

    private String storyLink;

    private String solutionReviewDate;

    private String tcReviewDate;

    private String techToTestDate;

    private String allToTestDate;

    private String prepubDate;

    private String productDate;

    private String owner;

    private List<UserDto> termPersons;

    private String pm;

    private String pd;

    private String backor;

    private String frontor;

    private String testor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getStoryLink() {
        return storyLink;
    }

    public void setStoryLink(String storyLink) {
        this.storyLink = storyLink;
    }

    public String getSolutionReviewDate() {
        return solutionReviewDate;
    }

    public void setSolutionReviewDate(String solutionReviewDate) {
        this.solutionReviewDate = solutionReviewDate;
    }

    public String getTcReviewDate() {
        return tcReviewDate;
    }

    public void setTcReviewDate(String tcReviewDate) {
        this.tcReviewDate = tcReviewDate;
    }

    public String getTechToTestDate() {
        return techToTestDate;
    }

    public void setTechToTestDate(String techToTestDate) {
        this.techToTestDate = techToTestDate;
    }

    public String getAllToTestDate() {
        return allToTestDate;
    }

    public void setAllToTestDate(String allToTestDate) {
        this.allToTestDate = allToTestDate;
    }

    public String getPrepubDate() {
        return prepubDate;
    }

    public void setPrepubDate(String prepubDate) {
        this.prepubDate = prepubDate;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<UserDto> getTermPersons() {
        return termPersons;
    }

    public void setTermPersons(List<UserDto> termPersons) {
        this.termPersons = termPersons;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getPd() {
        return pd;
    }

    public void setPd(String pd) {
        this.pd = pd;
    }

    public String getBackor() {
        return backor;
    }

    public void setBackor(String backor) {
        this.backor = backor;
    }

    public String getFrontor() {
        return frontor;
    }

    public void setFrontor(String frontor) {
        this.frontor = frontor;
    }

    public String getTestor() {
        return testor;
    }

    public void setTestor(String testor) {
        this.testor = testor;
    }

    //    public static ProjectTemplate convertTo(ProjectTemplateBody projectTemplateBody) {
//        ProjectTemplate projectTemplate = new ProjectTemplate();
//        projectTemplate.setStoryName(projectTemplateBody.getStoryName());
//        projectTemplate.setStoryLink(projectTemplateBody.getStoryLink());
//        projectTemplate.setSolutionReviewDate(projectTemplateBody.getSolutionReviewDate());
//        projectTemplate.setTcReviewDate(projectTemplateBody.getTcReviewDate());
//        projectTemplate.setTechToTestDate(projectTemplateBody.getTechToTestDate());
//        projectTemplate.setAllToTestDate(projectTemplateBody.getAllToTestDate());
//        projectTemplate.setPrepubDate(projectTemplateBody.getPrepubDate());
//        projectTemplate.setProductDate(projectTemplateBody.getProductDate());
//        projectTemplate.setOwner(projectTemplateBody.getOwner());
//        projectTemplate.setTermPersons(projectTemplateBody.getTermPersons().stream().collect(Collectors.joining(",")));
//        projectTemplate.setPm(projectTemplateBody.getPm());
//        projectTemplate.setPd(projectTemplateBody.getPd());
//        projectTemplate.setBackor(projectTemplateBody.getBackor());
//        projectTemplate.setFrontor(projectTemplateBody.getFrontor());
//        projectTemplate.setTestor(projectTemplateBody.getTestor());
//        projectTemplate.setUpdateTime(new Date());
//        return projectTemplate;
//    }
}
