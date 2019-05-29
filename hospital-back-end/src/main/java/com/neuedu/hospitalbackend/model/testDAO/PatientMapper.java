package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Patient;

public interface PatientMapper {
    int insert(Patient record);

    int insertSelective(Patient record);
}