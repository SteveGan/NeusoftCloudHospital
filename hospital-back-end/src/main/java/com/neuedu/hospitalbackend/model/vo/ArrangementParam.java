package com.neuedu.hospitalbackend.model.vo;


import com.neuedu.hospitalbackend.model.po.Arrangement;

import java.util.ArrayList;
import java.util.List;

public class ArrangementParam {

    private String startDate;

    private String endDate;

    private Integer id;//排班规则id

    private Integer departmentId;

    private List<Arrangement> arrangements = new ArrayList<>();
    //private Date appointmentDate;
    //private Integer roleId;
    //private String userName;
    //private Byte registrationLevelId;
    //private Boolean isValid;
    //private Byte timeSlot;
    //private Byte maxAppointment;
    //private Byte appointmentLeft;
    //private Integer departmentId;

    public List<Arrangement> getArrangements() {
        return arrangements;
    }

    public void setArrangements(List<Arrangement> arrangements) {
        this.arrangements = arrangements;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
