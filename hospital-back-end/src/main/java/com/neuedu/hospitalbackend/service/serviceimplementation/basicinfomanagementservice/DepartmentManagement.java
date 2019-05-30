package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.DepartmentMapper;
import com.neuedu.hospitalbackend.model.po.Department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentManagement {
    @Resource
    DepartmentMapper departmentMapper;

    /**
     * 根据部门id查询部分全部信息
     * @param id 部门id
     * @return
     */
    public Department selectDepartmentById(Integer id) {
        return departmentMapper.selectDepartmentById(id);
    }

    public int addDepartment(Department department) {
        return departmentMapper.addDepartment(department);
    }

}
