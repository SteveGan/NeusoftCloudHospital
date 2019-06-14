package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.InspectionTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface InspectionTemplateMapper {

    List<HashMap> getByRIdAndName(@Param("roleId") Integer roleId, @Param("name") String name);

    int insertSelective(InspectionTemplate inspectionTemplate);

}
