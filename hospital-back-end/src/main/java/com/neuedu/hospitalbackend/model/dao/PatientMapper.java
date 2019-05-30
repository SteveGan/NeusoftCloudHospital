package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Patient;

public interface PatientMapper {
    int insert(Patient record);

    int insertSelective(Patient record);
}