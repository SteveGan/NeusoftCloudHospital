package com.neuedu.hospitalbackend.model.vo;

import java.util.List;

public class CaseRecipesParam {
    private Integer caseId;
    private Integer roleId;
    private Integer recipeType;

    //不能用这个RecipeParam!!!!!!!!!
    List<RecipeParam> recipes;

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(Integer recipeType) {
        this.recipeType = recipeType;
    }

    public List<RecipeParam> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeParam> recipes) {
        this.recipes = recipes;
    }
}
