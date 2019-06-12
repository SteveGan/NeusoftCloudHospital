package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.RecipeTemplateMapper;
import com.neuedu.hospitalbackend.model.dao.RoleMapper;
import com.neuedu.hospitalbackend.model.po.Recipe;
import com.neuedu.hospitalbackend.model.po.RecipeTemplate;
import com.neuedu.hospitalbackend.model.vo.RecipeParam;
import com.neuedu.hospitalbackend.model.vo.RecipeTemplateParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.RecipeTemplateManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RecipeTemplateManagementServiceImpl implements RecipeTemplateManagementService {

    @Resource
    RecipeTemplateMapper recipeTemplateMapper;
    @Resource
    RoleMapper roleMapper;


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
            count = recipeTemplateMapper.insertSelective(recipeTemplate);
            if(count <= 0)
                return CommonResult.success(count);
        }
        return CommonResult.success(count);
    }

    /**
     * 删除模板
     */
    public CommonResult deleteRecipeTemplate(Integer roleId, String recipeName){
        return null;
    }

    /**
     * 查询处方模板
     * @param roleId
     */
    @Override
    public CommonResult listRecipeTemplate(Integer roleId, Integer type){
        JSONObject returnJson = new JSONObject();

        //获取医生所在科室id
        Integer departmentId = roleMapper.getDepartmentIdByRoleId(roleId);
        if (departmentId == null)
            return CommonResult.fail(ResultCode.E_800);

        //查询所有可见模板
        List<HashMap> recipeTemplates = recipeTemplateMapper.listAvailableByType(roleId, departmentId, type);
        List<HashMap> personal = new ArrayList<>();
        List<HashMap> department = new ArrayList<>();
        List<HashMap> hospital = new ArrayList<>();
        for(HashMap recipeTemplate : recipeTemplates){
            Integer scope = (Integer) recipeTemplate.get("scope");
            if(scope == 1)  //个人
                personal.add(recipeTemplate);
            else if (scope == 2) //科室
                department.add(recipeTemplate);
            else if (scope == 3)  //全院
                hospital.add(recipeTemplate);
        }

        returnJson.put("personal", personal);
        returnJson.put("department", department);
        returnJson.put("hospital", hospital);
        return CommonResult.success(returnJson);
    }



}
