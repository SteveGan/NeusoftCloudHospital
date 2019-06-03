package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.CaseTemplate;

public interface CaseTemplateMapper {
    int insert(CaseTemplate record);

    int insertSelective(CaseTemplate record);
}