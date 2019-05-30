package com.neuedu.hospitalbackend.model.po;

import javax.persistence.Column;
import java.sql.Date;
import java.sql.Timestamp;

public class Registration {
    @Column(name = "id")
    private Integer id;
    @Column(name = "patient_id")
    private Integer patientId;
    @Column(name = "appointment_date")
    private Date appointmentDate;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "registration_level_id")
    private Integer registrationLevelId;
    @Column(name = "department_id")
    private Integer departmentId;
    @Column(name = "total_fee")
    private double totalFee;
    @Column(name = "status")
    private Integer status;
    @Column(name = "cashier_id")
    private Integer cashierId;
    @Column(name = "pay_type")
    private Integer payType;
    @Column(name = "is_buy_case_book")
    private Integer isBuyCaseBook;
    @Column(name = "gmt_create")
    private Timestamp gmtCreate;
    @Column(name = "gmt_modified")
    private Timestamp gmtModified;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRegistrationLevelId() {
        return registrationLevelId;
    }

    public void setRegistrationLevelId(Integer registrationLevelId) {
        this.registrationLevelId = registrationLevelId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCashierId() {
        return cashierId;
    }

    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getIsBuyCaseBook() {
        return isBuyCaseBook;
    }

    public void setIsBuyCaseBook(Integer isBuyCaseBook) {
        this.isBuyCaseBook = isBuyCaseBook;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }
}
