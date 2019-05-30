package com.neuedu.hospitalbackend.model.po;

import java.math.BigDecimal;
import java.util.Date;

public class Recipe {
    private Integer id;

    private Integer caseId;

    private String medicineCode;

    private Boolean type;

    private BigDecimal dosage;

    private String frequency;

    private String dosageUnit;

    private Integer amount;

    private Integer returnAmount;

    private Boolean status;

    private Integer creatorRoleId;

    private Integer deliverRoleId;

    private Date gmtCreate;

    private Date gmtModified;

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

    public String getMedicineCode() {
        return medicineCode;
    }

    public void setMedicineCode(String medicineCode) {
        this.medicineCode = medicineCode == null ? null : medicineCode.trim();
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
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
        this.frequency = frequency == null ? null : frequency.trim();
    }

    public String getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit == null ? null : dosageUnit.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Integer returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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