package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.InspectionProject;

public interface InspectionProjectMapper {
    int insert(InspectionProject record);

    int insertSelective(InspectionProject record);
}