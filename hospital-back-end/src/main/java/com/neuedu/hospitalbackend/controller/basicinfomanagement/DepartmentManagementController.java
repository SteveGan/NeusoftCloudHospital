package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.DepartmentManagementImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Raven
 */
@RestController
@RequestMapping("/basicinfo")
@CrossOrigin
public class DepartmentManagementController {
    @Resource
    private DepartmentManagementImpl departmentManagement;

    @ApiOperation("1.2.1 根据id获取部门信息")
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public CommonResult selectDepartmentById(@PathVariable Integer id) {
        return departmentManagement.getDepartmentById(id);
    }

    @ApiOperation("1.2.2 新增部门信息")
    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public CommonResult addDepartment(@RequestBody Department department) {
        return departmentManagement.insertDepartment(department);
    }

    @ApiOperation("1.2.3 修改部门信息")
    @RequestMapping(value = "/department", method = RequestMethod.PUT)
    public CommonResult updateDepartment(@RequestBody Department department) {
        return departmentManagement.updateDepartmentById(department);
    }

    @ApiOperation("1.2.4 删除部门信息")
    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteDepartment(@PathVariable Integer id) {
        return departmentManagement.deleteDepartmentById(id);
    }

}
