package com.neuedu.hospitalbackend.model.vo;

public class DoctorParam {
    private Integer roleId;
    private String appointmentDateStr; //看诊时间
    private Integer appointmentLeft; //余号数量
    private Integer departmentId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getAppointmentDateStr() {
        return appointmentDateStr;
    }

    public void setAppointmentDateStr(String appointmentDateStr) {
        this.appointmentDateStr = appointmentDateStr;
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
