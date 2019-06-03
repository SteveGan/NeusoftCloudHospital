package com.neuedu.hospitalbackend.model.vo;

public class ProjectCheckInParam {
    /**
     * 检查 检验
     */
    private String projectType;
    private Integer collectionId;
    private Integer projectId;
    private Integer doctorRoleId;
    private String result;

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
