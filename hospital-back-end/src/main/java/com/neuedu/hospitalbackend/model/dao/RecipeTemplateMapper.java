package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.RecipeTemplate;

public interface RecipeTemplateMapper {
    int insert(RecipeTemplate record);

    int insertSelective(RecipeTemplate record);
}