package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Recipe;

public interface RecipeMapper {
    int insert(Recipe record);

    int insertSelective(Recipe record);
}