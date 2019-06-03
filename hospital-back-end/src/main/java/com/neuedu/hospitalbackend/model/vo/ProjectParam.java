package com.neuedu.hospitalbackend.model.vo;

public class ProjectParam {
    /**
     * 检查 检验
     */
    private String projectType;
    private Integer collectionId;
    private Integer projectId;
    private Integer doctorRoleId;
    /**
     * 结果
     */
    private String result_image;
    private String result_description;
    private String advice;

    public String getResult_image() {
        return result_image;
    }

    public void setResult_image(String result_image) {
        this.result_image = result_image;
    }

    public String getResult_description() {
        return result_description;
    }

    public void setResult_description(String result_description) {
        this.result_description = result_description;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
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
