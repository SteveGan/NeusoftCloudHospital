package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.PatientCase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PatientCaseMapper {

    int insert(PatientCase patientCase);
    int insertSelective(PatientCase patientCase);
    List<PatientCase> selectByCaseIdOrName(@Param("caseId") Integer caseId, @Param("patientName") String patientName);
    List<PatientCase> selectById(@Param("caseId")Integer caseId, @Param("departmentId") Integer departmentId);
    int deletePatientCaseById(Integer registrationId);
}
