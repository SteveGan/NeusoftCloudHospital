package com.neuedu.hospitalbackend.model.po;

import java.math.BigDecimal;
import java.sql.Date;

public class Registration {
    private Integer id;

    private Integer patientId;

    private Date appointmentDate;

    private Byte timeSlot;

    private Integer roleId;

    private Short registrationLevelId;

    private Integer departmentId;

    private BigDecimal totalFee;

    private Boolean isNormal;

    private Integer cashierId;

    private Byte payType;

    private Boolean isBuyCaseBook;

    private Date gmtCreate;

    private Date gmtModified;

    private PatientCase patientCase;

    //private Patient patient;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Byte getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Byte timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Short getRegistrationLevelId() {
        return registrationLevelId;
    }

    public void setRegistrationLevelId(Short registrationLevelId) {
        this.registrationLevelId = registrationLevelId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getCashierId() {
        return cashierId;
    }

    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
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

    public Boolean getNormal() {
        return isNormal;
    }

    public void setNormal(Boolean normal) {
        isNormal = normal;
    }

    public Boolean getBuyCaseBook() {
        return isBuyCaseBook;
    }

    public void setBuyCaseBook(Boolean buyCaseBook) {
        isBuyCaseBook = buyCaseBook;
    }
/*
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }*/

    public PatientCase getPatientCase() {
        return patientCase;
    }

    public void setPatientCase(PatientCase patientCase) {
        this.patientCase = patientCase;
    }

}