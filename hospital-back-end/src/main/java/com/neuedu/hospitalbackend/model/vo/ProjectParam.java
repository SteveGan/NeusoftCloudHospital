package com.neuedu.hospitalbackend.model.vo;

import java.util.List;

public class ProjectParam {

    private Integer projectId;//项目id
    private String projectName;//项目名称
    private Integer departmentId;//部门id
    private Integer doctorRoleId;//医技医生id
    private String goal;//目标
    private String requirement;//需求
    private String resultImage;//结果图片
    private String resultDescription;//结果描述
    private String advice;//医生建议

    private List<ItemsParam> items; //小项

    private Integer status;//状态

    private Integer caseId;//病历号
    private Integer creatorRoleId;//门诊医生id
    private Integer collectionId;//申请id
    private Integer projectType;//1.检查 2.检验 3.处置

    public List<ItemsParam> getItems() {
        return items;
    }

    public void setItems(List<ItemsParam> items) {
        this.items = items;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Integer getCreatorRoleId() {
        return creatorRoleId;
    }

    public void setCreatorRoleId(Integer creatorRoleId) {
        this.creatorRoleId = creatorRoleId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getResultImage() {
        return resultImage;
    }

    public void setResultImage(String resultImage) {
        this.resultImage = resultImage;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getDoctorRoleId() {
        return doctorRoleId;
    }

    public void setDoctorRoleId(Integer doctorRoleId) {
        this.doctorRoleId = doctorRoleId;
    }
}
