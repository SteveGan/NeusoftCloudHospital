package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Cost;

public interface CostMapper {
    int insert(Cost record);

    int insertSelective(Cost record);
}