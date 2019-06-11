package com.neuedu.hospitalbackend.model.vo;

import java.util.List;

public class RecipeCollectionParam {
    private Integer recipeId;
    private Integer creatorRoleId;
    private Integer caseId;
    private List<RecipeParam> medicines;

    public Integer getCreatorRoleId() {
        return creatorRoleId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public void setCreatorRoleId(Integer creatorRoleId) {
        this.creatorRoleId = creatorRoleId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public List<RecipeParam> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<RecipeParam> medicines) {
        this.medicines = medicines;
    }
}
