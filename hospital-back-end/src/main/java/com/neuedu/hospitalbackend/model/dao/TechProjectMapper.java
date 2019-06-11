package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.TechProject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface TechProjectMapper {

    BigDecimal getPriceByItemId(String itemId);

    int insert(TechProject record);

    int insertSelective(TechProject record);

    Integer getDepartmentIdByProjectId(Integer projectId);

    String getProjectNameByProjectId(Integer projectId);


    List<HashMap> listProjectBasicInfoByType(String classification);

    List<HashMap> listItemBasicInfoByProject(Integer projectId);

}
