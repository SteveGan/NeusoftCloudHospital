package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.neuedu.hospitalbackend.model.vo.RecipeTemplateParam;
import com.neuedu.hospitalbackend.util.CommonResult;

public interface RecipeTemplateManagementService {

    /**
     * 创建处方模板
     * @param recipeTemplateParam
     */
    CommonResult insertRecipeTemplate(RecipeTemplateParam recipeTemplateParam);

    /**
     * 删除处方模板
     */
    CommonResult deleteRecipeTemplate(Integer roleId, String recipeName);

    /**
     * 查询处方模板
     * @param roleId
     */
    CommonResult listRecipeTemplate(Integer roleId, Integer type);



}
