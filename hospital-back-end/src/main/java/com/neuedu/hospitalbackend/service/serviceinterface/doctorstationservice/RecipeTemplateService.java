package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.neuedu.hospitalbackend.model.vo.PatientCaseTemplateParam;
import com.neuedu.hospitalbackend.model.vo.RecipeTemplateParam;
import com.neuedu.hospitalbackend.util.CommonResult;



/**
 * 提供了关于处方模版的服务
 * @author Steve
 */
public interface RecipeTemplateService {


    /**
     * 保存处方模板
     * @param recipeTemplateParam 当前模版中的内容
     */
    CommonResult saveAsCaseTemplate(RecipeTemplateParam recipeTemplateParam);

    /**
     * 修改处方模板
     * @param recipeTemplateParam 模板修改后内容
     */
    CommonResult modifyPatientCaseTemplate(RecipeTemplateParam recipeTemplateParam);

    /**
     * 查询该医生所有可用处方模版
     * @param roleId 医生的角色id
     * @return 可用处方模版列表
     */
    CommonResult listPatientCaseTemplate(Integer roleId);

    /**
     * 删除处方模板
     * @param roleId,caseTemplateId
     */
    CommonResult deletePatientCaseTemplate(Integer roleId, String name);




}
