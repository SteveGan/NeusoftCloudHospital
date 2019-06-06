package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Diagnose;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiagnoseMapper {

    List<Integer> listDiseaseIdsByCaseId(Integer caseId);
    int insertSelective(Diagnose diagnose);
    int deleteByDiseaseId(Integer diseaseId);
    int updateExisted(@Param("diseaseId") Integer diseaseId, @Param("startTime") String startTime,
                      @Param("isFirstDiagnosed") Boolean isFirstDiagnosed);
    List<Diagnose> listDiagnosesByCaseId(Integer caseId);
}
