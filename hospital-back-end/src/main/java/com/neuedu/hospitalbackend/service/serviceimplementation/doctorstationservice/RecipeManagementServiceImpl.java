package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.neuedu.hospitalbackend.model.dao.RecipeMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.RecipeManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class RecipeManagementServiceImpl implements RecipeManagementService {

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public CommonResult listCurrentCaseRecipes(Integer caseId) {
        List<HashMap> recipes =  recipeMapper.listRecipeByRegistrationId(caseId);


        return null;
    }


}
