package com.neuedu.hospitalbackend.model.po;

import java.util.Date;

public class Arrangement {
    private Integer id;

    private Date appointmentDate;

    private Integer departmentId;

    private Integer roleId;

    private String userName;

    private Byte registrationLevelId;

    private Boolean isValid;

    private Byte timeSlot;

    private Byte maxAppointment;

    private Byte appointmentLeft;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Byte getRegistrationLevelId() {
        return registrationLevelId;
    }

    public void setRegistrationLevelId(Byte registrationLevelId) {
        this.registrationLevelId = registrationLevelId;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Byte getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Byte timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Byte getMaxAppointment() {
        return maxAppointment;
    }

    public void setMaxAppointment(Byte maxAppointment) {
        this.maxAppointment = maxAppointment;
    }

    public Byte getAppointmentLeft() {
        return appointmentLeft;
    }

    public void setAppointmentLeft(Byte appointmentLeft) {
        this.appointmentLeft = appointmentLeft;
    }
}