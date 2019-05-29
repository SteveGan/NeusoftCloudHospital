package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Inventory;

public interface InventoryMapper {
    int insert(Inventory record);

    int insertSelective(Inventory record);
}