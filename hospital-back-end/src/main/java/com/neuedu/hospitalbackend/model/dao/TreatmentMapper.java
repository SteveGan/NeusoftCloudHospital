package com.neuedu.hospitalbackend.model.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface TreatmentMapper {
    /**
     * 根据 病历号或者姓名 或 不输入 查找 待登记处置 用户
     * @param caseId
     * @param patientName
     * @return 待登记的caseId, patientName
     */
    List<HashMap> listPreparedPatientsByCaseIdOrName(@Param("caseId") Integer caseId,
                                                     @Param("patientName") String patientName);

    List<HashMap> listAppliedProjectsByCaseId(Integer caseId);

    int checkInProject(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                       @Param("treaterRoleId")Integer treaterRoleId);

    int cancelProject(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId);

    int selectStatusOfProject(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);

}
