package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.neuedu.hospitalbackend.model.dao.RecipeTemplateMapper;
import com.neuedu.hospitalbackend.model.po.RecipeTemplate;
import com.neuedu.hospitalbackend.model.vo.RecipeParam;
import com.neuedu.hospitalbackend.model.vo.RecipeTemplateParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.RecipeTemplateManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecipeTemplateManagementServiceImpl implements RecipeTemplateManagementService {

    @Resource
    private RecipeTemplateMapper recipeTemplateMapper;


    /**
     * 创建处方模板
     * @param recipeTemplateParam
     * @return
     */
    @Override
    public CommonResult insertRecipeTemplate(RecipeTemplateParam recipeTemplateParam){
        int count = 0;
        Integer roleId = recipeTemplateParam.getRoleId();
        Byte scope = recipeTemplateParam.getScope(); // 1.个人  2.科室  3.全院
        String name = recipeTemplateParam.getName();

        //参数检验
        if (roleId == null || scope == null || name == null){
            return CommonResult.fail(ResultCode.E_801);
        }
        //检验名称是否已存在
        if(recipeTemplateMapper.getRecipeTemplateByRoleIdAndName(roleId, name) != null)
            return CommonResult.fail(ResultCode.E_806);

        //插入处方模板
        List<RecipeParam> recipeParams = recipeTemplateParam.getRecipes();
        for(RecipeParam recipeParam : recipeParams){
            RecipeTemplate recipeTemplate = new RecipeTemplate();
            recipeTemplate.setName(name);
            recipeTemplate.setRoleId(roleId);
            recipeTemplate.setDepartmentId(132); //药局
            recipeTemplate.setScope(scope);
            recipeTemplate.setMedicineCode(recipeParam.getMedicineCode());
            recipeTemplate.setType(recipeParam.getType());
            recipeTemplate.setDosage(recipeParam.getDosage());
            recipeTemplate.setDosageUnit(recipeParam.getDosageUnit());
            recipeTemplate.setFrequency(recipeParam.getFrequency());
            recipeTemplate.setAmount(recipeParam.getAmount());
            count = recipeTemplateMapper.insert(recipeTemplate);
            if(count <= 0)
                return CommonResult.success(count);
        }

        return CommonResult.success(count);
    }


}
