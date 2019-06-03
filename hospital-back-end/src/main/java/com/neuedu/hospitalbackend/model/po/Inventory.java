package com.neuedu.hospitalbackend.model.po;

public class Inventory {
    private Integer id;

    private String medicineCode;

    private Short remainingAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicineCode() {
        return medicineCode;
    }

    public void setMedicineCode(String medicineCode) {
        this.medicineCode = medicineCode == null ? null : medicineCode.trim();
    }

    public Short getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Short remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}