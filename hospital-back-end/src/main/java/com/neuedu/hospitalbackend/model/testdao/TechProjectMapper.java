package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.TechProject;

public interface TechProjectMapper {
    int insert(TechProject record);

    int insertSelective(TechProject record);
}