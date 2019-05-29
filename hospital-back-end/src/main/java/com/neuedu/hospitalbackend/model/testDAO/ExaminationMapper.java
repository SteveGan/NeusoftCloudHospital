package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Examination;

public interface ExaminationMapper {
    int insert(Examination record);

    int insertSelective(Examination record);
}