package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.PatientCase;
import org.springframework.stereotype.Component;

@Component
public interface PatientCaseMapper {

    int insert(PatientCase patientCase);
    int insertSelective(PatientCase patientCase);
}
