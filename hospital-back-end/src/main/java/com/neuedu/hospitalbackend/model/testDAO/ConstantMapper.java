package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Constant;

public interface ConstantMapper {
    int insert(Constant record);

    int insertSelective(Constant record);
}