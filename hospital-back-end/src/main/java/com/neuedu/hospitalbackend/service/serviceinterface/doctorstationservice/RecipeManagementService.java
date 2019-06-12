package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.neuedu.hospitalbackend.model.vo.RecipeCollectionParam;
import com.neuedu.hospitalbackend.util.CommonResult;


/**
 * 提供医生端的药方管理服务方法
 * @author Steve
 */
public interface RecipeManagementService {

    /**
     * 返回当前case中的所有recipe
     * @param caseId 病历的id
     * @return 当前case中的所有recipe
     */
    CommonResult listCurrentCaseRecipes(Integer caseId);

    /**
     * 新增处方
     * @return 处方id
     */
    CommonResult insertNewRecipe();

    /**
     * 暂存处方
     * @param recipeCollectionParam
     * @return
     */
    CommonResult preserveRecipes(RecipeCollectionParam recipeCollectionParam);

    /**
     * 开立处方
     * @param recipeCollectionParam
     * @return
     */
    CommonResult submitRecipes(RecipeCollectionParam recipeCollectionParam);


//    CommonResult listPatients(Integer doctorRoleId);

//    /**
//     * 查询所有药品信息
//     * @return 所有药品
//     */
//    List<Medicine> listAllMedicine();
//
//
//    /**
//     * Add recipe into a case
//     * @param obj recipe的信息
//     */
//    void insertRecipe(JSONObject obj);
//
//    /**
//     * Update the recipe
//     * @param obj recipe的信息
//     */
//    void updateRecipe(JSONObject obj);


}
