package com.neuedu.hospitalbackend.model.dto;

import java.sql.Date;

public class DoctorParam {
    private Integer roleId;
    private Date time; //看诊时间
    private Integer appointmentLeft;
    private Integer departmentId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
