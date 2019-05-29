package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Inspection;

public interface InspectionMapper {
    int insert(Inspection record);

    int insertSelective(Inspection record);
}