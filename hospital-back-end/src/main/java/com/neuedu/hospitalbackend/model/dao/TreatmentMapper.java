package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Treatment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface TreatmentMapper {
    //列出某科室下所有的患者病历号和姓名
    List<HashMap> listPreparedPatientsByCaseIdOrDateOrName(@Param("caseId") Integer caseId, @Param("patientName") String patientName,
                                                           @Param("chargeDateStr") String chargeDateStr, @Param("departmentId") Integer departmentId);

    //列出指定患者的医技项目列表
    List<HashMap> listAllProjectsByCaseId(@Param("caseId") Integer caseId, @Param("chargeDateStr") String chargeDateStr, @Param("departmentId") Integer departmentId);

    //登记项目
    int checkInProject(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                       @Param("treaterRoleId")Integer treaterRoleId);

    //取消项目
    int cancelProject(@Param("collectionId")Integer collectionId, @Param("projectId")Integer projectId,
                      @Param("treaterRoleId")Integer treaterRoleId);

    //列出某科室所有已登记项目
    List<Treatment> listCheckedInButNotRecordedProjects(@Param("departmentId") Integer departmentId, @Param("dateStr") String dateStr);


    int updateStatus(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId, @Param("status") Byte status);

    Integer getLatestId();

    int insertSelective(Treatment treatment);

    List<Integer> listProjectIdsByCollectionId(Integer Id);

    int updateInfo(Treatment treatment);

    int delete(@Param("id") Integer id, @Param("projectId") Integer projectId);

    List<HashMap> listCollectionInfo(Integer caseId);

    List<HashMap> listProjectInfo(Integer collectionId);

    List<HashMap> listItems(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);


}
