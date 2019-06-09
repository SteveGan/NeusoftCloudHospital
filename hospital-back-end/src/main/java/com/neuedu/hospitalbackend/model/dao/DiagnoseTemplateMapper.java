package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.DiagnoseTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface DiagnoseTemplateMapper {

    DiagnoseTemplate getDiagnoseTemplateByName(String name);

    int insert(@Param("diseaseId") String diseaseIcdCode, @Param("diseaseName") String diseaseName,
            @Param("type") Byte type, @Param("roleId") Integer roleId, @Param("name") String name);

    List<String> listDiseaseIcdCodesByTemplateName(String name);

    int updateName(@Param("pastName") String pastName, @Param("newName") String newName);

    int deleteByDiseaseIcdCode(String icdCode);

    List<HashMap> listMyDiagnoseTemplate(Integer roleId);

    int deleteByName(String name);
}
