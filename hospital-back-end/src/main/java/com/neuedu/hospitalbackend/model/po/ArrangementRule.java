package com.neuedu.hospitalbackend.model.po;

public class ArrangementRule {
    private Integer id;

    private Integer roleId;

    private Byte isValid;

    private Integer departmentId;

    private Integer titleId;

    private Integer registrationLevelId;

    private String maxAppointment;

    private Integer adminId;

    private Byte monAm;

    private Byte monPm;

    private Byte tueAm;

    private Byte tuePm;

    private Byte wedAm;

    private Byte wedPm;

    private Byte thuAm;

    private Byte thuPm;

    private Byte friAm;

    private Byte friPm;

    private Byte satAm;

    private Byte satPm;

    private Byte sunAm;

    private Byte sunPm;

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

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
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

    public Byte getMonAm() {
        return monAm;
    }

    public void setMonAm(Byte monAm) {
        this.monAm = monAm;
    }

    public Byte getMonPm() {
        return monPm;
    }

    public void setMonPm(Byte monPm) {
        this.monPm = monPm;
    }

    public Byte getTueAm() {
        return tueAm;
    }

    public void setTueAm(Byte tueAm) {
        this.tueAm = tueAm;
    }

    public Byte getTuePm() {
        return tuePm;
    }

    public void setTuePm(Byte tuePm) {
        this.tuePm = tuePm;
    }

    public Byte getWedAm() {
        return wedAm;
    }

    public void setWedAm(Byte wedAm) {
        this.wedAm = wedAm;
    }

    public Byte getWedPm() {
        return wedPm;
    }

    public void setWedPm(Byte wedPm) {
        this.wedPm = wedPm;
    }

    public Byte getThuAm() {
        return thuAm;
    }

    public void setThuAm(Byte thuAm) {
        this.thuAm = thuAm;
    }

    public Byte getThuPm() {
        return thuPm;
    }

    public void setThuPm(Byte thuPm) {
        this.thuPm = thuPm;
    }

    public Byte getFriAm() {
        return friAm;
    }

    public void setFriAm(Byte friAm) {
        this.friAm = friAm;
    }

    public Byte getFriPm() {
        return friPm;
    }

    public void setFriPm(Byte friPm) {
        this.friPm = friPm;
    }

    public Byte getSatAm() {
        return satAm;
    }

    public void setSatAm(Byte satAm) {
        this.satAm = satAm;
    }

    public Byte getSatPm() {
        return satPm;
    }

    public void setSatPm(Byte satPm) {
        this.satPm = satPm;
    }

    public Byte getSunAm() {
        return sunAm;
    }

    public void setSunAm(Byte sunAm) {
        this.sunAm = sunAm;
    }

    public Byte getSunPm() {
        return sunPm;
    }

    public void setSunPm(Byte sunPm) {
        this.sunPm = sunPm;
    }
}