package com.neuedu.hospitalbackend.model.testPO;

public class ArrangementRule {
    private Integer id;

    private Integer roleId;

    private Boolean isValid;

    private Integer departmentId;

    private Integer titleId;

    private Integer registrationLevelId;

    private String maxAppointment;

    private Integer adminId;

    private Boolean monAm;

    private Boolean monPm;

    private Boolean tueAm;

    private Boolean tuePm;

    private Boolean wedAm;

    private Boolean wedPm;

    private Boolean thuAm;

    private Boolean thuPm;

    private Boolean friAm;

    private Boolean friPm;

    private Boolean satAm;

    private Boolean satPm;

    private Boolean sunAm;

    private Boolean sunPm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public Integer getRegistrationLevelId() {
        return registrationLevelId;
    }

    public void setRegistrationLevelId(Integer registrationLevelId) {
        this.registrationLevelId = registrationLevelId;
    }

    public String getMaxAppointment() {
        return maxAppointment;
    }

    public void setMaxAppointment(String maxAppointment) {
        this.maxAppointment = maxAppointment == null ? null : maxAppointment.trim();
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Boolean getMonAm() {
        return monAm;
    }

    public void setMonAm(Boolean monAm) {
        this.monAm = monAm;
    }

    public Boolean getMonPm() {
        return monPm;
    }

    public void setMonPm(Boolean monPm) {
        this.monPm = monPm;
    }

    public Boolean getTueAm() {
        return tueAm;
    }

    public void setTueAm(Boolean tueAm) {
        this.tueAm = tueAm;
    }

    public Boolean getTuePm() {
        return tuePm;
    }

    public void setTuePm(Boolean tuePm) {
        this.tuePm = tuePm;
    }

    public Boolean getWedAm() {
        return wedAm;
    }

    public void setWedAm(Boolean wedAm) {
        this.wedAm = wedAm;
    }

    public Boolean getWedPm() {
        return wedPm;
    }

    public void setWedPm(Boolean wedPm) {
        this.wedPm = wedPm;
    }

    public Boolean getThuAm() {
        return thuAm;
    }

    public void setThuAm(Boolean thuAm) {
        this.thuAm = thuAm;
    }

    public Boolean getThuPm() {
        return thuPm;
    }

    public void setThuPm(Boolean thuPm) {
        this.thuPm = thuPm;
    }

    public Boolean getFriAm() {
        return friAm;
    }

    public void setFriAm(Boolean friAm) {
        this.friAm = friAm;
    }

    public Boolean getFriPm() {
        return friPm;
    }

    public void setFriPm(Boolean friPm) {
        this.friPm = friPm;
    }

    public Boolean getSatAm() {
        return satAm;
    }

    public void setSatAm(Boolean satAm) {
        this.satAm = satAm;
    }

    public Boolean getSatPm() {
        return satPm;
    }

    public void setSatPm(Boolean satPm) {
        this.satPm = satPm;
    }

    public Boolean getSunAm() {
        return sunAm;
    }

    public void setSunAm(Boolean sunAm) {
        this.sunAm = sunAm;
    }

    public Boolean getSunPm() {
        return sunPm;
    }

    public void setSunPm(Boolean sunPm) {
        this.sunPm = sunPm;
    }
}