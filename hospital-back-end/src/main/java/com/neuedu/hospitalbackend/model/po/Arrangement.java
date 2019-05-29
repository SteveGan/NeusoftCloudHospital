package com.neuedu.hospitalbackend.model.po;

import javax.persistence.Column;
import java.sql.Date;

public class Arrangement {

    @Column(name = "id")
    private Integer id;
    @Column(name = "time")
    private Date time;
    @Column(name = "department_id")
    private Integer departmentId;
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "registration_level_id")
    private Integer registrationLevelId;
    @Column(name = "is_valid")
    private Integer isValid;
    @Column(name = "time_slot")
    private String timeSlot;
    @Column(name = "max_appointment")
    private Integer maxAppointment;
    @Column(name = "appointment_left")
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
        this.userName = userName;
    }

    public Integer getRegistrationLevelId() {
        return registrationLevelId;
    }

    public void setRegistrationLevelId(Integer registrationLevelId) {
        this.registrationLevelId = registrationLevelId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
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
