package com.neuedu.hospitalbackend.model.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class RecipeParam {

    private Integer id;

    private Integer caseId;

    private Integer medicineId;

    private Integer departmentId;

    private Byte type;

    private BigDecimal dosage;

    private String frequency;

    private String dosageUnit;

    private Short amount;

    private Short returnAmount;

    private Byte status;

    private Byte transactionLogStatus;

    private Integer creatorRoleId;

    private Integer deliverRoleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public BigDecimal getDosage() {
        return dosage;
    }

    public void setDosage(BigDecimal dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    public Short getAmount() {
        return amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }

    public Short getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Short returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getTransactionLogStatus() {
        return transactionLogStatus;
    }

    public void setTransactionLogStatus(Byte transactionLogStatus) {
        this.transactionLogStatus = transactionLogStatus;
    }

    public Integer getCreatorRoleId() {
        return creatorRoleId;
    }

    public void setCreatorRoleId(Integer creatorRoleId) {
        this.creatorRoleId = creatorRoleId;
    }

    public Integer getDeliverRoleId() {
        return deliverRoleId;
    }

    public void setDeliverRoleId(Integer deliverRoleId) {
        this.deliverRoleId = deliverRoleId;
    }
}
