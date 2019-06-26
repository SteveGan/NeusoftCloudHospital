package com.neuedu.hospitalbackend.model.po;

import java.sql.Date;

public class PatientCase {
    private Integer registrationId;

    private Integer patientId;

    private Integer roleId;

    private Byte status;

    private String narrate;

    private String curDisease;

    private String curTreatCondition;

    private String pastDisease;

    private String allergy;

    private String physicalCondition;

    private Date appointmentDate;

    private Date gmtCreate;

    private Date gmtModified;

    private String patientName;

    private String assistDiagnose;

    private Registration registration;

    public String getAssistDiagnose() {
        return assistDiagnose;
    }

    public void setAssistDiagnose(String assistDiagnose) {
        this.assistDiagnose = assistDiagnose;
    }

    public Integer getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public String getCurTreatCondition() {
        return curTreatCondition;
    }

    public void setCurTreatCondition(String curTreatCondition) {
        this.curTreatCondition = curTreatCondition == null ? null : curTreatCondition.trim();
    }

    public String getPastDisease() {
        return pastDisease;
    }

    public void setPastDisease(String pastDisease) {
        this.pastDisease = pastDisease == null ? null : pastDisease.trim();
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy == null ? null : allergy.trim();
    }

    public String getPhysicalCondition() {
        return physicalCondition;
    }

    public void setPhysicalCondition(String physicalCondition) {
        this.physicalCondition = physicalCondition == null ? null : physicalCondition.trim();
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}