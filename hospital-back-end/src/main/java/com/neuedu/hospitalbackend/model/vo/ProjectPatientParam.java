package com.neuedu.hospitalbackend.model.vo;


/**
 * 检查检验项目
 */
public class ProjectPatientParam {

    /**
     * 检查 检验
     */
    private String projectType;
    private Integer caseId;
    private String patientName;


    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}