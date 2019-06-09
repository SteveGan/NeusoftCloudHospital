package com.neuedu.hospitalbackend.model.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface RecipeMapper {

    int updateStatus(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId, @Param("status") Byte status);
    int updateAmount(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId, @Param("returnAmount") Short returnAmount);
    Byte selectStatusByCollectionAndProjectId(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);
}
