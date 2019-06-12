package com.neuedu.hospitalbackend.model.vo;


import java.util.ArrayList;
import java.util.List;

public class RecipeTemplateParam {
    private String name;
    private Integer roleId;
    private Integer departmentId;
    private Byte scope; //1.个人 2.科室 3.全院

    private List<RecipeParam> recipes = new ArrayList<>();
//    private String medicineCode;
//    private Byte type; //1.中草药 2.西药
//    private BigDecimal dosage;
//    private String dosageUnit;
//    private String frequency;
//    private Short amount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<RecipeParam> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeParam> recipes) {
        this.recipes = recipes;
    }
}
