package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.TreatmentTemplate;

public interface TreatmentTemplateMapper {
    int insert(TreatmentTemplate record);

    int insertSelective(TreatmentTemplate record);
}