package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.ArrangementRule;

import java.util.HashMap;
import java.util.List;

public interface ArrangementRuleMapper {

    int insertSelective(ArrangementRule arrangementRule);

    Integer getLastId();

    List<HashMap> getValidById(Integer id);

    List<HashMap> listArrangementRulesByDepartmentId(Integer departmentId);

}
