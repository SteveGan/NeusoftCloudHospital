package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Department;

public interface DepartmentMapper {
    int insert(Department record);

    int insertSelective(Department record);
}