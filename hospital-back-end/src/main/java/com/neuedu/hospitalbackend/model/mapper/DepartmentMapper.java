package com.neuedu.hospitalbackend.model.mapper;

import com.neuedu.hospitalbackend.model.po.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {
    public Department selectDepartmentById(String id);
}
