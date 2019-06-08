package com.neuedu.hospitalbackend.model.po;

import java.util.Date;

public class CaseTemplate {
    private Integer id;

    private Integer roleId;

    private Integer departmentId;

    private String name;

    private Byte scope;

    private String narrate;

    private String curDisease;

    private String physicalCondition;

    private String curTreatCondition;

    private String pastDisease;

    private String allergy;

    private String assistDiagnose;

    private Date gmtCreate;

    private Date gmtModified;

    public String getCurTreatCondition() {
        return curTreatCondition;
    }

    public void setCurTreatCondition(String curTreatCondition) {
        this.curTreatCondition = curTreatCondition;
    }

    public String getPastDisease() {
        return pastDisease;
    }

    public void setPastDisease(String pastDisease) {
        this.pastDisease = pastDisease;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getAssistDiagnose() {
        return assistDiagnose;
    }

    public void setAssistDiagnose(String assistDiagnose) {
        this.assistDiagnose = assistDiagnose;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getScope() {
        return scope;
    }

    public void setScope(Byte scope) {
        this.scope = scope;
    }

    public String getNarrate() {
        return narrate;
    }

    public void setNarrate(String narrate) {
        this.narrate = narrate == null ? null : narrate.trim();
    }

    public String getCurDisease() {
        return curDisease;
    }

    public void setCurDisease(String curDisease) {
        this.curDisease = curDisease == null ? null : curDisease.trim();
    }

    public String getPhysicalCondition() {
        return physicalCondition;
    }

    public void setPhysicalCondition(String physicalCondition) {
        this.physicalCondition = physicalCondition == null ? null : physicalCondition.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}