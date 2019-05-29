package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.InspectionTemplate;

public interface InspectionTemplateMapper {
    int insert(InspectionTemplate record);

    int insertSelective(InspectionTemplate record);
}