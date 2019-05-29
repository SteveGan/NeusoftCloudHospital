package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Diagnose;

public interface DiagnoseMapper {
    int insert(Diagnose record);

    int insertSelective(Diagnose record);
}