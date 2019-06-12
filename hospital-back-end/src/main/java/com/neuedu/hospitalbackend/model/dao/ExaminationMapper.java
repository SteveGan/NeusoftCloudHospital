package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Examination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface ExaminationMapper {

    //列出某科室下所有的患者病历号和姓名
    List<HashMap> listPreparedPatientsByCaseIdOrDateOrName(@Param("caseId") Integer caseId, @Param("patientName") String patientName,
                                                           @Param("chargeDateStr") String chargeDateStr, @Param("departmentId") Integer departmentId);

    List<HashMap> listAllProjectsByCaseId(@Param("caseId") Integer caseId, @Param("chargeDateStr") String chargeDateStr, @Param("departmentId") Integer departmentId);

    int checkInProject(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                       @Param("examinerRoleId")Integer examinerRoleId);

    int cancelProject(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                      @Param("examinerRoleId")Integer examinerRoleId);

    List<Examination> listCheckedInButNotRecordedProjects(@Param("departmentId") Integer departmentId, @Param("dateStr") String dateStr);

    int updateResult(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                     @Param("resultDescription")String resultDescription, @Param("resultImage")String resultImage,
                     @Param("advice")String advice);

    int updateStatus(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId, @Param("status") Byte status);

    int getStatusOfProject(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);

    Integer getLatestId();

    int insertSelective(Examination examination);

    List<Integer> listProjectIdsByCollectionId(Integer Id);

    int updateInfo(Examination examination);

    int delete(@Param("id") Integer id, @Param("projectId") Integer projectId);

    int deleteByCollectionId(Integer collectionId);

    List<HashMap> listCollectionInfo(Integer caseId);

    List<HashMap> listProjectInfo(Integer collectionId);

    List<HashMap> listItems(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);

}
