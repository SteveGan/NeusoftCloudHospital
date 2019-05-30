package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Inventory;

public interface InventoryMapper {
    int insert(Inventory record);

    int insertSelective(Inventory record);
}