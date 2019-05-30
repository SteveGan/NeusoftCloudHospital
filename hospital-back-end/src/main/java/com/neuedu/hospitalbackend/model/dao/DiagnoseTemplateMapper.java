package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.DiagnoseTemplate;

public interface DiagnoseTemplateMapper {
    int insert(DiagnoseTemplate record);

    int insertSelective(DiagnoseTemplate record);
}