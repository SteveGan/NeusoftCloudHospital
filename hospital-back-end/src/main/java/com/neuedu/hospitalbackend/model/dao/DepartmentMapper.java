package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {
    Department selectDepartmentById(Integer id);

    int addDepartment(Department department);
}
