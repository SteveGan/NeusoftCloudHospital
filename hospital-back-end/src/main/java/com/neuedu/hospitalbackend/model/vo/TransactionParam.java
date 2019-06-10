package com.neuedu.hospitalbackend.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionParam {

    private Integer id; //缴费记录id
    private String invoiceCode;
    private Integer registrationId;
    private Integer patientId;
    private Integer roleId;
    private String type;
    private Integer collectionId;
    private Integer projectId;
    private String itemId;
    private Short amount;
    private Byte payType;
    private BigDecimal totalMoney;
    private Byte status; //缴费记录状态: 未缴费，已缴费...
    private Byte itemStatus; //对应收费项目状态: 开立，已登记...
    private Byte itemCategory; //检查项目，检验项目，处置项目，药方
    private Short returnAmount; //退费项目的数量
    private Integer departmentId; //执行科室

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
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
        this.type = type;
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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
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

    public Byte getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Byte itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Byte getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(Byte itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Short getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Short returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}


