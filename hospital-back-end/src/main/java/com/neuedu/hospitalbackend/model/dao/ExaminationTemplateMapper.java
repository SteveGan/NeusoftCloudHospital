package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.ExaminationTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ExaminationTemplateMapper {

    List<HashMap> getByRIdAndName(@Param("roleId") Integer roleId, @Param("name") String name);

    int insertSelective(ExaminationTemplate examinationTemplate);

    List<HashMap> getInfoByRoleIdAndDepartmentId(@Param("roleId") Integer roleId, @Param("departmentId") Integer departmentId);

    List<HashMap> listTemplateNameAndCreator(@Param("roleId") Integer roleId, @Param("departmentId") Integer departmentId);

    List<HashMap> listProject(@Param("roleId") Integer roleId, @Param("name") String templateName);

    List<HashMap> listItems(@Param("roleId") Integer roleId, @Param("name") String name, @Param("projectId") Integer projectId);

    int deleteTemplateByRoleIdAndName(@Param("roleId") Integer roleId, @Param("name") String name);

}
