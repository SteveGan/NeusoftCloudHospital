package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.InspectionProject;

public interface InspectionProjectMapper {
    int insert(InspectionProject record);

    int insertSelective(InspectionProject record);
}