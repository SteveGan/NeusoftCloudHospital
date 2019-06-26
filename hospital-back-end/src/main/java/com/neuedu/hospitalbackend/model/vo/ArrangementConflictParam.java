package com.neuedu.hospitalbackend.model.vo;

import java.sql.Date;

public class ArrangementConflictParam {

    private String userName;

    private String departmentName;

    private Date date;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
