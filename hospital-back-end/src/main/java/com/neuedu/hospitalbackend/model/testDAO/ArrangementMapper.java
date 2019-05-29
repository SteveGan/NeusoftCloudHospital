package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Arrangement;

public interface ArrangementMapper {
    int insert(Arrangement record);

    int insertSelective(Arrangement record);
}