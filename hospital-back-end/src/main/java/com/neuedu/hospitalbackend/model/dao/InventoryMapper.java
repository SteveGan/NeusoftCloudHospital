package com.neuedu.hospitalbackend.model.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface InventoryMapper {
    //根据药品id查询药品库存
    Short getRemainingAmountByMedicineId(Integer medicineId);
    //更新库存
    int updateInventory(@Param("medicineId") Integer medicineId, @Param("amount") Short amount);
}
