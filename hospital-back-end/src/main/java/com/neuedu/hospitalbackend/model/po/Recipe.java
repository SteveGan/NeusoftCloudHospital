package com.neuedu.hospitalbackend.model.po;

import java.math.BigDecimal;
import java.sql.Date;

public class Recipe {

    private Integer id;

    private Integer caseId;

    private Integer medicineId;

    private Integer departmentId;

    private Short amount;

    private Short remainAmount;

    private Byte medicineType;

    private String medicineSpecification;

    private String medicineUnit;

    private String medicineFormulation;

    private BigDecimal medicineUnitPrice;

    private BigDecimal dosage;

    private String frequency;

    private Byte status;

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

    public Short getAmount() {
        return amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }

    public Short getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Short remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Byte getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(Byte medicineType) {
        this.medicineType = medicineType;
    }

    public String getMedicineSpecification() {
        return medicineSpecification;
    }

    public void setMedicineSpecification(String medicineSpecification) {
        this.medicineSpecification = medicineSpecification;
    }

    public String getMedicineUnit() {
        return medicineUnit;
    }

    public void setMedicineUnit(String medicineUnit) {
        this.medicineUnit = medicineUnit;
    }

    public String getMedicineFormulation() {
        return medicineFormulation;
    }

    public void setMedicineFormulation(String medicineFormulation) {
        this.medicineFormulation = medicineFormulation;
    }

    public BigDecimal getMedicineUnitPrice() {
        return medicineUnitPrice;
    }

    public void setMedicineUnitPrice(BigDecimal medicineUnitPrice) {
        this.medicineUnitPrice = medicineUnitPrice;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
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