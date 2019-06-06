package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Diagnose;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiagnoseMapper {

    int insertSelective(Diagnose diagnose);
    int deleteByDiseaseIcdCode(String diseaseIcdCoded);
    int updateExisted(@Param("diseaseIcdCode") String diseaseIcdCode, @Param("startTime") String startTime,
                      @Param("isFirstDiagnosed") Boolean isFirstDiagnosed);
    List<String> listDiseaseIcdCodesByCaseId(Integer caseId);
    List<Diagnose> listDiagnosesByCaseId(Integer caseId);
}
