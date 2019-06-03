package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.model.po.DepartmentExample;

public interface DepartmentMapper {
    int countByExample(DepartmentExample example);

    int insert(Department record);

    int insertSelective(Department record);
}