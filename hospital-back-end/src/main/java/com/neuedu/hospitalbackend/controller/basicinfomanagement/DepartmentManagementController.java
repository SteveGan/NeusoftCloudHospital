package com.neuedu.hospitalbackend.controller.basicinfomanagement;

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
    public CommonResult selectDepartmentById(@PathVariable Integer id) {
        CommonResult result = departmentManagement.getDepartmentById(id);
        return result;
    }

    @ApiOperation("新增部门信息")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult<Integer> addDepartment(@RequestBody Department department) {
        int count = departmentManagement.insertDepartment(department);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

}
