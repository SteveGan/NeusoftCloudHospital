package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.ExaminationTemplate;

public interface ExaminationTemplateMapper {
    int insert(ExaminationTemplate record);

    int insertSelective(ExaminationTemplate record);
}