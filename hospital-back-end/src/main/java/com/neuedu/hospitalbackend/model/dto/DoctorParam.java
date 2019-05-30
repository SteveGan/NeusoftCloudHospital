package com.neuedu.hospitalbackend.model.dto;

import java.sql.Date;

public class DoctorParam {
    private Integer roleId;
    private String appointmentTimeStr; //看诊时间
    private Integer appointmentLeft;
    private Integer departmentId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getAppointmentTimeStr() {
        return appointmentTimeStr;
    }

    public void setAppointmentTimeStr(String appointmentTimeStr) {
        this.appointmentTimeStr = appointmentTimeStr;
    }

    public Integer getAppointmentLeft() {
        return appointmentLeft;
    }

    public void setAppointmentLeft(Integer appointmentLeft) {
        this.appointmentLeft = appointmentLeft;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

}
