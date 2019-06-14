package com.neuedu.hospitalbackend.model.vo;

import java.util.ArrayList;
import java.util.List;

public class CollectionTemplateParam {

    private Integer type; //1.检查 2.检验 3.处置

    private String name; //模板原名称

    private String newName; //模板更改后名字

    private Integer roleId;

    private Integer departmentId;

    private Byte scope; //权限范围：1.个人 2.科室 3.全院

    List<ProjectTemplateParam> projects = new ArrayList<>();

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Byte getScope() {
        return scope;
    }

    public void setScope(Byte scope) {
        this.scope = scope;
    }

    public List<ProjectTemplateParam> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectTemplateParam> projects) {
        this.projects = projects;
    }
}
