package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Cost;

public interface CostMapper {
    int insert(Cost record);

    int insertSelective(Cost record);
}