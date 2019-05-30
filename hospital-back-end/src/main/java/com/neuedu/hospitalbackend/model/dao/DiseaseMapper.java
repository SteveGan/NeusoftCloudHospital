package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Disease;

public interface DiseaseMapper {
    int insert(Disease record);

    int insertSelective(Disease record);
}