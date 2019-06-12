package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.RecipeTemplate;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;

public interface RecipeTemplateMapper {

    int insert(RecipeTemplate recipeTemplate);

    int insertSelective(RecipeTemplate recipeTemplate);

    List<HashMap> getRecipeTemplateByRoleIdAndName(@Param("roleId") Integer roleId, @Param("name") String name);

    List<HashMap> listAvailableByType(Integer roleId, Integer departmentId, Integer type);

    int deleteByRIdAndName(@Param("roleId") Integer roleId, @Param("recipeName") String recipeName);
}
