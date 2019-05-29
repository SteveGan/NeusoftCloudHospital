package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.CaseTemplate;

public interface CaseTemplateMapper {
    int insert(CaseTemplate record);

    int insertSelective(CaseTemplate record);
}