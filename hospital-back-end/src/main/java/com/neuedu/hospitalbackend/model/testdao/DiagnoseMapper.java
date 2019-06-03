package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Diagnose;

public interface DiagnoseMapper {
    int insert(Diagnose record);

    int insertSelective(Diagnose record);
}