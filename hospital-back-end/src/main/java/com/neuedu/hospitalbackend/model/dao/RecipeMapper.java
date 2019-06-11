package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.vo.RecipeParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface RecipeMapper {

    int updateRemainAmount(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId, @Param("returnAmount") Short returnAmount);

    //根据患者的病历号，列出处方信息列表
    List<HashMap> listRecipeByRegistrationId(Integer registrationId);
    //退费时，更新对应的处方状态
    int updateStatus(@Param("collectionId") Integer collectionId, @Param("medicineId") Integer medicineId, @Param("status") Byte status);
    //取药或退药时，更新对应的处方状态和发药人id
    int updateStatusAndDeliverId(@Param("collectionId") Integer collectionId, @Param("medicineId") Integer medicineId,
                                 @Param("status") Byte status, @Param("deliverId") Integer deliverId);
    //更新库存
    int updateInventory(@Param("medicineId") Integer medicineId, @Param("amount") Short amount);
    //列出指定日期或指定病历号的处方信息
    List<HashMap> listRecipesByRegistrationIdOrDate(@Param("caseId") Integer caseId, @Param("chargeDateBeginStr") String chargeDateBeginStr, @Param("chargeDateEndStr") String chargeDateEndStr);

}
