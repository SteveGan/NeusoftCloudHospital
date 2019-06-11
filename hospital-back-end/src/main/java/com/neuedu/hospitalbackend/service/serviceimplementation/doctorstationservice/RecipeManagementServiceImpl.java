package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.RecipeMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.RecipeManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class RecipeManagementServiceImpl implements RecipeManagementService {

    @Resource
    private RecipeMapper recipeMapper;

    @Override
    public CommonResult listCurrentCaseRecipes(Integer caseId) {
        JSONObject returnJson = new JSONObject();
        List<HashMap> recipes =  recipeMapper.listRecipeByRegistrationId(caseId);
        if(recipes == null)
            returnJson.put("type", 0);
        else{
            String type = (String)recipes.get(0).get("medicineType");
            if(type == "中草药")
                returnJson.put("type", 1);
            else
                returnJson.put("type", 2);
        }
        returnJson.put("recipes", recipes);
        return null;
    }


}
