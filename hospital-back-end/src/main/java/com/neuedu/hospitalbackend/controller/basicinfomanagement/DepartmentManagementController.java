package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.DepartmentManagementImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 1.2 科室管理
 * @author Raven
 */
@RestController
@RequestMapping("/basicinfo")
@CrossOrigin
public class DepartmentManagementController {
    @Resource
    private DepartmentManagementImpl departmentManagement;

    @ApiOperation("1.2.1 根据id获取科室信息")
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public CommonResult getDepartmentById(@PathVariable Integer id) {
        return departmentManagement.getDepartmentById(id);
    }

    @ApiOperation("1.2.2 新增科室信息")
    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public CommonResult addDepartment(@RequestBody Department department) {
        return departmentManagement.insertDepartment(department);
    }

    @ApiOperation("1.2.3 修改科室信息")
    @RequestMapping(value = "/department", method = RequestMethod.PUT)
    public CommonResult updateDepartment(@RequestBody Department department) {
        return departmentManagement.updateDepartmentById(department);
    }

    @ApiOperation("1.2.4 删除科室信息")
    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteDepartment(@PathVariable Integer id) {
        return departmentManagement.deleteDepartmentById(id);
    }

    @ApiOperation("1.2.5 列出全部科室")
    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public CommonResult listAllDepartments() {
        return departmentManagement.listAllDepartments();
    }

    @ApiOperation("1.2.6 分页列出科室")
    @RequestMapping(value = "/departments/{num}/{size}", method = RequestMethod.GET)
    public CommonResult listAllDepartments(@PathVariable Integer num, @PathVariable Integer size) {
        return departmentManagement.listPage(num, size);
    }

    @ApiOperation("1.2.7 树状图列出科室")
    @RequestMapping(value = "/departments/tree", method = RequestMethod.GET)
    public CommonResult listDepartmentsTree() {
        return departmentManagement.listDepartmentsTree();
    }

    @ApiOperation("获取所有门诊科室")
    @RequestMapping(value = "/doctordepartments", method = RequestMethod.GET)
    public CommonResult listDoctorDepartments() {
        return departmentManagement.listDoctorDepartments();
    }


}
