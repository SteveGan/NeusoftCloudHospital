package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
public interface PatientMapper {
    int insert(Patient patient);
    int insertSelective(Patient patient);
    Integer getPatientByIdCard(String idCard);
    Integer getNextId();
}
