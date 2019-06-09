package com.neuedu.hospitalbackend.model.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface RecipeMapper {

    int updateAmount(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId, @Param("returnAmount") Short returnAmount);
    List<HashMap> listRecipeByRegistrationId(Integer registrationId);
    int updateStatus(@Param("collectionId") Integer collectionId, @Param("medicineId") Integer medicineId, @Param("status") Byte status);
    int updateStatusAndDeliverId(@Param("collectionId") Integer collectionId, @Param("medicineId") Integer medicineId,
                                 @Param("status") Byte status, @Param("deliverId") Integer deliverId);
    int updateInventory(@Param("medicineId") Integer medicineId, @Param("amount") Short amount);
    Byte selectStatusByCollectionAndProjectId(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);
}
