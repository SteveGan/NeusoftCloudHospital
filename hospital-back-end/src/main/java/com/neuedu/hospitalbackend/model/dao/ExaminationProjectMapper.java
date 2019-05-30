package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.ExaminationProject;

public interface ExaminationProjectMapper {
    int insert(ExaminationProject record);

    int insertSelective(ExaminationProject record);
}