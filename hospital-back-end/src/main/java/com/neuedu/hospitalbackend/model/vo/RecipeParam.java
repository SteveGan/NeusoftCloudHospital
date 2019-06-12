package com.neuedu.hospitalbackend.model.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class RecipeParam {
    private String medicineCode;

    private Byte type; //1.中草药 2.西药

    private String dosageUnit;

    private Integer id;

    private Integer caseId;

    private Integer medicineId;

    private Integer departmentId;

    private Short amount;

    private Short remainAmount; //剩余可退的数量

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

    private Short returnAmount; //退药的数量

    private Byte transactionLogStatus;

    private String chargeDateStr; //收费日期

   /* private String chargeDateBeginStr;

    private String chargeDateEndStr;
*/
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

    public String getMedicineCode() {
        return medicineCode;
    }

    public void setMedicineCode(String medicineCode) {
        this.medicineCode = medicineCode;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit;
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

    public Short getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Short returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Byte getTransactionLogStatus() {
        return transactionLogStatus;
    }

    public void setTransactionLogStatus(Byte transactionLogStatus) {
        this.transactionLogStatus = transactionLogStatus;
    }

    public String getChargeDateStr() {
        return chargeDateStr;
    }

    public void setChargeDateStr(String chargeDateStr) {
        this.chargeDateStr = chargeDateStr;
    }

   /* public String getChargeDateBeginStr() {
        return chargeDateBeginStr;
    }

    public void setChargeDateBeginStr(String chargeDateBeginStr) {
        this.chargeDateBeginStr = chargeDateBeginStr;
    }

    public String getChargeDateEndStr() {
        return chargeDateEndStr;
    }

    public void setChargeDateEndStr(String chargeDateEndStr) {
        this.chargeDateEndStr = chargeDateEndStr;
    }*/
}
