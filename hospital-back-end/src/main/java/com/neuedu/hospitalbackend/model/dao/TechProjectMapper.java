package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.TechProject;

import java.math.BigDecimal;

public interface TechProjectMapper {

    BigDecimal getPriceByItemId(String itemId);

    int insert(TechProject record);

    int insertSelective(TechProject record);

    Integer getDepartmentIdByProjectId(Integer projectId);

    String getProjectNameByProjectId(Integer projectId);

}
