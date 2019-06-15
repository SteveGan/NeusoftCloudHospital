package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.InspectionTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface InspectionTemplateMapper {

    List<HashMap> getByRIdAndName(@Param("roleId") Integer roleId, @Param("name") String name);

    int insertSelective(InspectionTemplate inspectionTemplate);

    List<HashMap> getInfoByRoleIdAndDepartmentId(@Param("roleId") Integer roleId, @Param("departmentId") Integer departmentId);

    List<HashMap> listTemplateNameAndCreator(@Param("roleId") Integer roleId, @Param("departmentId") Integer departmentId);

    List<HashMap> listProject(@Param("roleId") Integer roleId, @Param("name") String templateName);

    List<HashMap> listItems(@Param("roleId") Integer roleId, @Param("name") String name, @Param("projectId") Integer projectId);

    int deleteTemplateByRoleIdAndName(@Param("roleId") Integer roleId, @Param("name") String name);

}
