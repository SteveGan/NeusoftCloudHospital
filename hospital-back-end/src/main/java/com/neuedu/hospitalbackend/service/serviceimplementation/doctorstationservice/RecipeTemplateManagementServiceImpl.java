package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.Map;

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
        String name = recipeTemplateParam.getNewName();

        //参数检验
        if (roleId == null || scope == null || name == null){
            return CommonResult.fail(ResultCode.E_801);
        }
        //检验名称是否已存在
        if(0 != recipeTemplateMapper.getRecipeTemplateByRoleIdAndName(roleId, name).size())
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
        //参数检验
        if (roleId == null || recipeName == null)
            return CommonResult.fail(ResultCode.E_801);
        //权限检验
        if(0 == recipeTemplateMapper.getRecipeTemplateByRoleIdAndName(roleId, recipeName).size())
            return CommonResult.fail(ResultCode.E_804);
        //删除模板
        int count = recipeTemplateMapper.deleteByRIdAndName(roleId, recipeName);
        return CommonResult.success(count);
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
        HashMap<String, List<HashMap>> personal = new HashMap<>();
        HashMap<String, List<HashMap>> department = new HashMap<>();
        HashMap<String, List<HashMap>> hospital = new HashMap<>();
        //模板名称+内容组
        for(HashMap recipeTemplate : recipeTemplates){
            String recipeName = (String)recipeTemplate.get("name");
            Integer scope = (Integer) recipeTemplate.get("scope");
            recipeTemplate.remove("name");
            recipeTemplate.remove("scope");
            recipeTemplate.remove("roleId");
            recipeTemplate.remove("departmentId");
            List<HashMap> medicines;
            if(scope == 1) { //个人
                System.out.println(personal);
                if(personal.containsKey(recipeName))
                    medicines = personal.get(recipeName);
                else
                    medicines = new ArrayList<>();
                medicines.add(recipeTemplate);
                personal.put(recipeName, medicines);
            }
            if (scope == 2) {  //科室
                if(department.containsKey(recipeName)) {
                    medicines = personal.get(recipeName);
                    medicines.add(recipeTemplate);
                }
                else {
                    medicines = new ArrayList<>();
                    medicines.add(recipeTemplate);
                    department.put(recipeName, medicines);
                }
            }
            if (scope == 3) {  //全院
                if(hospital.containsKey(recipeName)) {
                    medicines = personal.get(recipeName);
                    medicines.add(recipeTemplate);
                }
                else {
                    medicines = new ArrayList<>();
                    medicines.add(recipeTemplate);
                    hospital.put(recipeName, medicines);
                }
            }
        }

        returnJson.put("personal", toJson(personal));
        returnJson.put("department", toJson(department));
        returnJson.put("hospital", toJson(hospital));
        return CommonResult.success(returnJson);
    }


    /**
     * 修改处方模板
     * @param recipeTemplateParam
     */
    public CommonResult modifyRecipeTemplate(RecipeTemplateParam recipeTemplateParam){
        Integer roleId = recipeTemplateParam.getRoleId();
        String pastName = recipeTemplateParam.getName();//修改前名称
        String newName = recipeTemplateParam.getNewName();//修改后名称

        //参数检验
        if(roleId == null || pastName == null || newName == null)
            return CommonResult.fail(ResultCode.E_801);

        //修改名称是否已存在
        if (!newName.equals(pastName) &&
                0 != recipeTemplateMapper.getRecipeTemplateByRoleIdAndName(roleId, newName).size())
            return CommonResult.fail(ResultCode.E_806);

        //删除
        if (200 != deleteRecipeTemplate(roleId, pastName).getCode())
            return CommonResult.fail();

        //增加
        return insertRecipeTemplate(recipeTemplateParam);
    }



    /**
     * HashMap转成Json格式
     * @param hashMap
     * @return
     */
    public JSONArray toJson(HashMap<String, List<HashMap>> hashMap){
        JSONArray returnArray = new JSONArray();
        for (Map.Entry<String, List<HashMap>> entry : hashMap.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", entry.getKey());
            jsonObject.put("newName", "");
            jsonObject.put("medicines", entry.getValue());
            returnArray.add(jsonObject);
        }
        return returnArray;
    }

}
