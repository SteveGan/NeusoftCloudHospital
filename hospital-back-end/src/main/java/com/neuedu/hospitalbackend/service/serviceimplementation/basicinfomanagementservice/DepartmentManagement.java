package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.DepartmentMapper;
import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.DepartmentManagementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentManagement implements DepartmentManagementService {
    @Resource
    DepartmentMapper departmentMapper;

    /**
     * 根据部门id查询部分全部信息
     * @param id 部门id
     * @return
     */
    @Override
    public Department getDepartmentById(Integer id) {
        return null;
    }

    @Override
    public int insertDepartment(Department department) {
        return 0;
    }

    @Override
    public int updateDepartmentById(Department department) {
        return 0;
    }

    @Override
    public int deleteDepartmentById(Integer id) {
        return 0;
    }
}
