package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Inventory;

public interface InventoryMapper {
    int insert(Inventory record);

    int insertSelective(Inventory record);
}