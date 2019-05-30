package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Constant;

public interface ConstantMapper {
    int insert(Constant record);

    int insertSelective(Constant record);
}