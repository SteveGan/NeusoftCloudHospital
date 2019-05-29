package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.RecipeTemplate;

public interface RecipeTemplateMapper {
    int insert(RecipeTemplate record);

    int insertSelective(RecipeTemplate record);
}