package com.neuedu.hospitalbackend.model.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ExaminationMapper {
    /**
     * 根据 病历号或者姓名 或 不输入 查找 待登记检验 用户
     * @param caseId
     * @param patientName
     * @return 待登记的caseId, patientName
     */
    List<HashMap> listPreparedExaminationPatientsByCaseIdOrName(@Param("caseId") Integer caseId, @Param("patientName") String patientName);
}
