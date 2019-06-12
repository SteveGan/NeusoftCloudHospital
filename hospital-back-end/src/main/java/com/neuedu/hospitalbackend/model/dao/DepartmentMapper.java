package com.neuedu.hospitalbackend.model.dao;

import com.github.pagehelper.Page;
import com.neuedu.hospitalbackend.model.po.Department;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DepartmentMapper {
    Department get(Integer id);

    int insert(Department department);

    int update(Department department);

    int delete(Integer id);

    List<Department> list();

    Page<Department> listByPage();

    String getClassificationById(Integer id);
}
