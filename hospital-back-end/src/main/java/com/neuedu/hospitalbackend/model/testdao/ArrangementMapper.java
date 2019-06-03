package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Arrangement;

public interface ArrangementMapper {
    int insert(Arrangement record);

    int insertSelective(Arrangement record);
}