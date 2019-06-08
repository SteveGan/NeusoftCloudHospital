package com.neuedu.hospitalbackend.model.vo;

import java.util.List;

public class DiagnoseTemplateParam {

    private Integer roleId;
    private String name;
    private String newName;
    private List<DiagnoseParam> diagnoseList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<DiagnoseParam> getDiagnoseList() {
        return diagnoseList;
    }

    public void setDiagnoseList(List<DiagnoseParam> diagnoseList) {
        this.diagnoseList = diagnoseList;
    }
}
