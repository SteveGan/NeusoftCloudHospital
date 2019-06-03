package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Treatment;

public interface TreatmentMapper {
    int insert(Treatment record);

    int insertSelective(Treatment record);
}