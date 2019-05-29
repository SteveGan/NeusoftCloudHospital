package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Recipe;

public interface RecipeMapper {
    int insert(Recipe record);

    int insertSelective(Recipe record);
}