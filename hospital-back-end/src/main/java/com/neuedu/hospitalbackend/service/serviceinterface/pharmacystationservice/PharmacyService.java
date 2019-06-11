package com.neuedu.hospitalbackend.service.serviceinterface.pharmacystationservice;

import com.neuedu.hospitalbackend.model.vo.RecipeParam;
import com.neuedu.hospitalbackend.util.CommonResult;

import java.util.List;

public interface PharmacyService {

    /**
     *
     * @param caseId 病历号
     * @param chargeDateStr 收费日期
     * @return
     */
    CommonResult listRecipesByRegistrationIdOrDate(Integer caseId, String chargeDateStr);
    /**
     * 根据患者的病历号和指定时间，列出处方信息列表
     * @param registrationId 病历号
     * @return
     */
    CommonResult listIndividualRecipe(Integer registrationId);

    /**
     * 执行发药操作
     * @param recipeParams 需要缴费的项目列表
     * @return
     */
    CommonResult deliverMedicine(List<RecipeParam> recipeParams);

    /**
     * 执行退药操作
     * @param recipeParams 需要退费的项目列表
     * @return
     */
    CommonResult returnMedicine(List<RecipeParam> recipeParams);
}
