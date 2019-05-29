package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.PatientCase;

public interface PatientCaseMapper {
    int insert(PatientCase record);

    int insertSelective(PatientCase record);
}