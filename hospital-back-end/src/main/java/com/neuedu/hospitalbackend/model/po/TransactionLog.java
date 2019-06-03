package com.neuedu.hospitalbackend.model.po;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionLog {
    private Integer id;

    private String invoiceCode;

    private Integer registrationId;

    private Integer patientId;

    private Integer roleId;

    private String type;

    private Integer collectionId;

    private Integer projectId;

    private Integer itemId;

    private Short amount;

    private Byte payType;

    private BigDecimal totalMoney;

    private Byte status;

    private Date gmtCreate;

    private Date gmtModified;

    public Integer getId() {
        return id;
    }

    public TransactionLog(){

    }

    public TransactionLog(String invoiceCode, Integer registrationId, Integer patientId, Integer roleId, String type, Integer collectionId, Integer projectId, Integer itemId, Short amount, Byte payType, BigDecimal totalMoney, Byte status) {
        this.invoiceCode = invoiceCode;
        this.registrationId = registrationId;
        this.patientId = patientId;
        this.roleId = roleId;
        this.type = type;
        this.collectionId = collectionId;
        this.projectId = projectId;
        this.itemId = itemId;
        this.amount = amount;
        this.payType = payType;
        this.totalMoney = totalMoney;
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode == null ? null : invoiceCode.trim();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Short getAmount() {
        return amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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