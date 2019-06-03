package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Constant;

public interface ConstantMapper {
    int insert(Constant record);

    int insertSelective(Constant record);
}