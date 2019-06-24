package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.PatientCase;
import com.neuedu.hospitalbackend.model.vo.PatientCaseParam;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface PatientCaseMapper {

    int insert(PatientCase patientCase);
    int insertSelective(PatientCase patientCase);

    List<HashMap> listWaitingPatients(Integer doctorRoleId);
    List<HashMap> listWaitedPatients(Integer doctorRoleId);
    HashMap getPatientCaseInfo(Integer caseId);
    int getPatientCaseStatus(Integer caseId);
    int updatePatientCase(@Param("caseId") Integer caseId, @Param("narrate") String narrate, @Param("curDisease") String curDisease,
                               @Param("curTreatCondition") String curTreatCondition, @Param("pastDisease") String pastDisease,
                               @Param("allergy") String allergy, @Param("physicalCondition") String physicalCondition,
                               @Param("assistDiagnose") String assistDiagnose, @Param("status") Integer status);
    int updatePatientCaseStatus(@Param("caseId") Integer caseId, @Param("status") Integer status);
    int deletePatientCaseById(Integer registrationId);
    Integer getPatientIdByCaseId(Integer caseId);

    //小程序--列出指定患者之前的等待人数
    HashMap getWaitingAmountById(Integer registrationId);
}
