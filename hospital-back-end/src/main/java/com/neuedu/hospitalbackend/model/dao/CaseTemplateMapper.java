package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.bo.PatientCaseTemplate;
import com.neuedu.hospitalbackend.model.po.CaseTemplate;
import com.neuedu.hospitalbackend.model.vo.PatientCaseTemplateParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CaseTemplateMapper {

    String getNameById(Integer id);
    CaseTemplate getPatientCaseTemplateByName(String name);
    int insert(PatientCaseTemplateParam patientCaseTemplateParam);
    int update(PatientCaseTemplateParam patientCaseTemplateParam);
    List<PatientCaseTemplate> listAvailableByRoleId(@Param("roleId") Integer roleId, @Param("departmentId") Integer departmentId);

}
