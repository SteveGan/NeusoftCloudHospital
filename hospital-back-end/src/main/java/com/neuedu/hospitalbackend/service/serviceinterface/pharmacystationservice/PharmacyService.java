package com.neuedu.hospitalbackend.service.serviceinterface.pharmacystationservice;

import com.neuedu.hospitalbackend.model.vo.RecipeParam;
import com.neuedu.hospitalbackend.util.CommonResult;

import java.util.List;

public interface PharmacyService {

    /**
     * 根据患者的病历号，查询开立的处方信息
     * @param registrationId 病历号
     * @return
     */
    CommonResult listRecipeByRegistrationId(Integer registrationId);

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
