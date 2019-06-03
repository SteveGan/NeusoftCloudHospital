package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Cost;

public interface CostMapper {
    int insert(Cost record);

    int insertSelective(Cost record);
}