package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.DepartmentManagement;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Raven
 */
@RestController
@RequestMapping("/department")
@CrossOrigin
public class DepartmentManagementController {
    @Resource
    private DepartmentManagement departmentManagement;

    @RequestMapping("/{id}")
    public CommonResult<Department> getDepartment(@PathVariable Integer id)
    {
        return CommonResult.success(departmentManagement.selectDepartmentById(id));
    }

}
