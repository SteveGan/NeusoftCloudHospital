package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Recipe;

public interface RecipeMapper {
    int insert(Recipe record);

    int insertSelective(Recipe record);
}