package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.PatientCase;

public interface PatientCaseMapper {
    int insert(PatientCase record);

    int insertSelective(PatientCase record);
}