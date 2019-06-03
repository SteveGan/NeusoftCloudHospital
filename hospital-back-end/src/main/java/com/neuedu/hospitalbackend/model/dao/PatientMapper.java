package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Patient;
import org.springframework.stereotype.Component;

@Component
public interface PatientMapper {
    int insert(Patient patient);
    Integer getPatientByIdCard(String idCard);
}
