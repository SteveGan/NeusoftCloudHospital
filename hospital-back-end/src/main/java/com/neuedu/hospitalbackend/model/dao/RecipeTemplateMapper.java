package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.RecipeTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;

public interface RecipeTemplateMapper {

    int insert(RecipeTemplate recipeTemplate);

    int insertSelective(RecipeTemplate recipeTemplate);

    HashMap getRecipeTemplateByRoleIdAndName(@Param("roleId") Integer roleId, @Param("name") String name);

}
