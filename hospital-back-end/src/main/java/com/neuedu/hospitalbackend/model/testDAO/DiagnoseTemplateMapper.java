package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.DiagnoseTemplate;

public interface DiagnoseTemplateMapper {
    int insert(DiagnoseTemplate record);

    int insertSelective(DiagnoseTemplate record);
}