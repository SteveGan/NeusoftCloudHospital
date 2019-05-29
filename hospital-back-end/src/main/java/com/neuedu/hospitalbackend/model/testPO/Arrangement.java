package com.neuedu.hospitalbackend.model.testPO;

import java.util.Date;

public class Arrangement {
    private Integer id;

    private Date time;

    private Integer departmentId;

    private Integer roleId;

    private String userName;

    private Boolean registrationLevelId;

    private Byte isValid;

    private String timeSlot;

    private Integer maxAppointment;

    private Integer appointmentLeft;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public Boolean getRegistrationLevelId() {
        return registrationLevelId;
    }

    public void setRegistrationLevelId(Boolean registrationLevelId) {
        this.registrationLevelId = registrationLevelId;
    }

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot == null ? null : timeSlot.trim();
    }

    public Integer getMaxAppointment() {
        return maxAppointment;
    }

    public void setMaxAppointment(Integer maxAppointment) {
        this.maxAppointment = maxAppointment;
    }

    public Integer getAppointmentLeft() {
        return appointmentLeft;
    }

    public void setAppointmentLeft(Integer appointmentLeft) {
        this.appointmentLeft = appointmentLeft;
    }
}