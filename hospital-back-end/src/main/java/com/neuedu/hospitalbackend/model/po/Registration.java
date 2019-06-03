package com.neuedu.hospitalbackend.model.po;

import java.math.BigDecimal;
import java.util.Date;

public class Registration {
    private Integer id;

    private Integer patientId;

    private Date appointmentDate;

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

    public Boolean getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(Boolean isNormal) {
        this.isNormal = isNormal;
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

    public Boolean getIsBuyCaseBook() {
        return isBuyCaseBook;
    }

    public void setIsBuyCaseBook(Boolean isBuyCaseBook) {
        this.isBuyCaseBook = isBuyCaseBook;
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