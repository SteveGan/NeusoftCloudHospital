package com.neuedu.hospitalbackend.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class RegistrationParam {

    private Integer registrationId; //病历号
    private String name; //患者姓名
    private Byte gender; //患者性别
    private String birthdayStr; //患者出生日期
    private Byte payType; //结算类别
    private String idCard; //患者身份证号
    private String address; //家庭地址
    private String appointmentDateStr; //患者看诊日期
    private Byte timeSlot; //患者看诊时间段 （1.上午，2.下午）
    private Short registrationLevelId; //号别
    private Integer departmentId; //科室
    private String doctorName; //看诊医生姓名
    private Integer roleId; //看诊医生角色id
    private BigDecimal totalFee; //应收金额
    private Boolean isBuyCaseBook;
    private Integer cashierId; //收银员id
    private String invoiceCode; //当前挂号单对应的发票号
    
    private Integer patientId; //病人在本医院的id
    private Byte patientCaseStatus; //患者病历状态

    public Integer getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppointmentDateStr() {
        return appointmentDateStr;
    }

    public void setAppointmentDateStr(String appointmentDateStr) {
        this.appointmentDateStr = appointmentDateStr;
    }

    public Byte getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Byte timeSlot) {
        this.timeSlot = timeSlot;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public Boolean getIsBuyCaseBook() {
        return isBuyCaseBook;
    }

    public void setIsBuyCaseBook(Boolean isBuyCaseBook) {
        this.isBuyCaseBook = isBuyCaseBook;
    }

    public Integer getCashierId() {
        return cashierId;
    }

    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public Byte getPatientCaseStatus() {
        return patientCaseStatus;
    }

    public void setPatientCaseStatus(Byte patientCaseStatus) {
        this.patientCaseStatus = patientCaseStatus;
    }
}