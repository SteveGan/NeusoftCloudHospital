package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Treatment;

public interface TreatmentMapper {
    int insert(Treatment record);

    int insertSelective(Treatment record);
}