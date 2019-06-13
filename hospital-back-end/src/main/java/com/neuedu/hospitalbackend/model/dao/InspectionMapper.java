package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Inspection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface InspectionMapper {

    //列出某科室下所有的患者病历号和姓名
    List<HashMap> listPreparedPatientsByCaseIdOrDateOrName(@Param("caseId") Integer caseId, @Param("patientName") String patientName,
                                                     @Param("chargeDateStr") String chargeDateStr, @Param("departmentId") Integer departmentId);

    //列出指定患者的医技项目列表
    List<HashMap> listAllProjectsByCaseId(@Param("caseId") Integer caseId, @Param("chargeDateStr") String chargeDateStr, @Param("departmentId") Integer departmentId);

    //登记项目
    int checkInProject(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                       @Param("inspectorRoleId")Integer inspectorRoleId);

    //取消项目
    int cancelProject(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                      @Param("inspectorRoleId")Integer inspectorRoleId);

    //列出某科室所有已登记项目
    List<Inspection> listCheckedInButNotRecordedProjects(@Param("departmentId") Integer departmentId, @Param("dateStr") String dateStr);

    //更新某项目结果
    int updateResult(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                     @Param("resultDescription")String resultDescription, @Param("resultImage")String resultImage,
                     @Param("advice")String advice);


    int getStatusOfProject(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);

    int updateStatus(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId, @Param("status") Byte status);

    int updateStatusToCancel(@Param("collectionId") Integer collectionId, @Param("status") Byte status);

    Integer getLatestId();

    int insertSelective(Inspection inspection);

    List<Integer> listProjectIdsByCollectionId(Integer Id);

    int updateInfo(Inspection inspection);

    int delete(@Param("id") Integer id, @Param("projectId") Integer projectId);

    int deleteByCollectionId(Integer collectionId);

    List<HashMap> listCollectionInfo(Integer caseId);

    List<HashMap> listProjectInfo(Integer collectionId);

    List<HashMap> listItems(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);

    List<HashMap> getResult(Integer caseId);

    HashMap getResultByCIdAndPId(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);


}
