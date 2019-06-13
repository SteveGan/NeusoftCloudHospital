package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Diagnose;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface DiagnoseMapper {

    int insertSelective(Diagnose diagnose);

    int deleteByCaseIdAndDiseaseIcdCode(@Param("caseId") Integer caseId, @Param("diseaseIcdCoded") String diseaseIcdCoded);

    int deleteFinalDiagnose(Integer caseId);

    int updateExisted(@Param("diseaseIcdCode") String diseaseIcdCode, @Param("startTime") String startTime,
                      @Param("isFirstDiagnosed") Boolean isFirstDiagnosed);

    List<String> listDiseaseIcdCodesByCaseId(Integer caseId);

    List<HashMap> listDiagnosesDetailByCaseId(Integer caseId);

    List<HashMap> listFinalDiagnoseByCaseId(Integer caseId);
}
