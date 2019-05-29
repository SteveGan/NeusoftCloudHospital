package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.ExaminationProject;

public interface ExaminationProjectMapper {
    int insert(ExaminationProject record);

    int insertSelective(ExaminationProject record);
}