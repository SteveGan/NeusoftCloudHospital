package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Department;

public interface DepartmentMapper {
    int insert(Department record);

    int insertSelective(Department record);
}