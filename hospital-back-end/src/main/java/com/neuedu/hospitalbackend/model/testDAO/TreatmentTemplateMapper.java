package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.TreatmentTemplate;

public interface TreatmentTemplateMapper {
    int insert(TreatmentTemplate record);

    int insertSelective(TreatmentTemplate record);
}