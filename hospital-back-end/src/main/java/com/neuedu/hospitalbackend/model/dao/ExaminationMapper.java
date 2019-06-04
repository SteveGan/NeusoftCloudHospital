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
    List<HashMap> listPreparedPatientsByCaseIdOrName(@Param("caseId") Integer caseId, @Param("patientName") String patientName,
                                                     @Param("departmentId") Integer departmentId);

    List<HashMap> listAppliedProjectsByCaseId(@Param("caseId") Integer caseId, @Param("departmentId") Integer departmentId);

    int checkInProject(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                       @Param("examinerRoleId")Integer examinerRoleId);

    int cancelProject(@Param("collectionId")Integer collectionId,@Param("projectId") Integer projectId);

    List<HashMap> listCheckedInButNotRecordedProject(@Param("caseId") Integer caseId, @Param("departmentId") Integer departmentId);

    int recordResult(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                     @Param("resultDescription")String resultDescription, @Param("resultImage")String resultImage,
                     @Param("advice")String advice);

    int selectStatusOfProject(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);
}
