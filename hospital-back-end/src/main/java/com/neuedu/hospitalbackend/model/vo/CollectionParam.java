package com.neuedu.hospitalbackend.model.vo;

import java.util.List;

public class CollectionParam {

    private Integer roleId;//门诊医生
    private Integer collectionId;//申请id
    private Integer caseId;//病历号
    private Byte status;//状态
    private Integer applicantRoleId;//门诊医生
    private Integer collectionType;//1.检查 2.检验 3.处置

    private List<ProjectParam> projects;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getApplicantRoleId() {
        return applicantRoleId;
    }

    public void setApplicantRoleId(Integer applicantRoleId) {
        this.applicantRoleId = applicantRoleId;
    }

    public Integer getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Integer collectionType) {
        this.collectionType = collectionType;
    }

    public List<ProjectParam> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectParam> projects) {
        this.projects = projects;
    }
}
