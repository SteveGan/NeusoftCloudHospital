package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.InspectionTemplate;

public interface InspectionTemplateMapper {
    int insert(InspectionTemplate record);

    int insertSelective(InspectionTemplate record);
}