package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.ExaminationTemplate;

public interface ExaminationTemplateMapper {
    int insert(ExaminationTemplate record);

    int insertSelective(ExaminationTemplate record);
}