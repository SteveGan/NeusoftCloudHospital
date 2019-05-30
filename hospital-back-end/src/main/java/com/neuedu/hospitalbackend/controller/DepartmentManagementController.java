package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.DepartmentManagement;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Raven
 */
@RestController
@RequestMapping("/department-management")
@CrossOrigin
public class DepartmentManagementController {
    @Resource
    private DepartmentManagement departmentManagement;

    @ApiOperation("根据id获取部门信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<Department> selectDepartmentById(@PathVariable Integer id) {
        Department department = departmentManagement.selectDepartmentById(id);
        return CommonResult.success(department);
    }

    @ApiOperation("新增部门信息")
    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public CommonResult<Integer> addDepartment(@RequestBody Department department) {
        int count = departmentManagement.addDepartment(department);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

}
