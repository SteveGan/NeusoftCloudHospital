package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Patient;
import org.springframework.stereotype.Component;

@Component
public interface PatientMapper {
    int insert(Patient patient);
    int insertSelective(Patient patient);
    Integer getPatientByIdCard(String idCard);
    Integer getNextId();
    Patient getPatientByRegistrationId(Integer registrationId);
    int updatePatientInfo(Patient patient);
}
