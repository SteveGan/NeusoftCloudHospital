package com.neuedu.hospitalbackend.model.po;

public class Inventory {
    private Integer id;

    private Integer medicineId;

    private Short remainingAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Short getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Short remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}