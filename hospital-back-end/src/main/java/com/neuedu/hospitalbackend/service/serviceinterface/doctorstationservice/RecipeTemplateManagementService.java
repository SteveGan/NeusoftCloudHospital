package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.neuedu.hospitalbackend.model.vo.RecipeTemplateParam;
import com.neuedu.hospitalbackend.util.CommonResult;

public interface RecipeTemplateManagementService {

    /**
     * 创建处方模板
     * @param recipeTemplateParam
     */
    CommonResult insertRecipeTemplate(RecipeTemplateParam recipeTemplateParam);


}
