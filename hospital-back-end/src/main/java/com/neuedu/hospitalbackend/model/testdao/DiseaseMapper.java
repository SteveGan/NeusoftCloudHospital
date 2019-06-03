package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Disease;

public interface DiseaseMapper {
    int insert(Disease record);

    int insertSelective(Disease record);
}