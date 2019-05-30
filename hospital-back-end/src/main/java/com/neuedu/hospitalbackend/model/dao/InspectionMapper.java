package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Inspection;

public interface InspectionMapper {
    int insert(Inspection record);

    int insertSelective(Inspection record);
}