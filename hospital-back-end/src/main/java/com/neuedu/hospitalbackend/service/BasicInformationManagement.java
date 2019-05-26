package com.neuedu.hospitalbackend.service;

import com.neuedu.hospitalbackend.model.mapper.DepartmentMapper;
import com.neuedu.hospitalbackend.model.po.Department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Raven
 */
@Service
public class BasicInformationManagement {
    @Resource DepartmentMapper departmentMapper;

    public Department selectDepartmentById(Department department) {
        return departmentMapper.selectDepartmentById(department);
    }
}
