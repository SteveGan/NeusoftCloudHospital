package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.PatientCase;

public interface PatientCaseMapper {
    int insert(PatientCase record);

    int insertSelective(PatientCase record);
}