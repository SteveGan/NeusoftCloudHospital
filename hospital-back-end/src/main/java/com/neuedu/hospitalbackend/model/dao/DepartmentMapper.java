package com.neuedu.hospitalbackend.model.dao;

import com.github.pagehelper.Page;
import com.neuedu.hospitalbackend.model.po.Department;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    Department get(Integer id);

    int insert(Department department);

    int update(Department department);

    int delete(Integer id);

    List<Department> list();

    Page<Department> listByPage();
}
