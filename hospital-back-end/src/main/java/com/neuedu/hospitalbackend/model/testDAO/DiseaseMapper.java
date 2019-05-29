package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Disease;

public interface DiseaseMapper {
    int insert(Disease record);

    int insertSelective(Disease record);
}