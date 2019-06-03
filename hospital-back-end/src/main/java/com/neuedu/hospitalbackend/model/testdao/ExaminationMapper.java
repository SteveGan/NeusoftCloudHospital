package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Examination;

public interface ExaminationMapper {
    int insert(Examination record);

    int insertSelective(Examination record);
}