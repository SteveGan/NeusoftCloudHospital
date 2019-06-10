package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.bo.PatientCaseTemplate;
import com.neuedu.hospitalbackend.model.po.CaseTemplate;
import com.neuedu.hospitalbackend.model.vo.PatientCaseTemplateParam;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CaseTemplateMapper {

    String getNameById(Integer id);
    CaseTemplate getPatientCaseTemplateByRoleIdAndName(@Param("roleId") Integer roleId, @Param("name") String name);
    int insert(PatientCaseTemplateParam patientCaseTemplateParam);
    int update(PatientCaseTemplateParam patientCaseTemplateParam);
    int deleteById(Integer id);
    List<HashMap> listAvailableByRoleId(@Param("roleId") Integer roleId, @Param("departmentId") Integer departmentId);
    List<HashMap> listMyCaseTemplates(Integer roleId);
    List<HashMap> listDepartmentCaseTemplates(@Param("roleId") Integer roleId, @Param("departmentId") Integer departmentId);
    List<HashMap> listHospitalCaseTemplates(Integer roleId);
    Integer getRoleIdById(Integer id);
}
