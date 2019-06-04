package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.UserManagementImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 1.3 用户管理
 * @author Raven
 */
@RestController
@RequestMapping("/basicinfo")
@CrossOrigin
public class UserManagementController {
    @Resource
    private UserManagementImpl userManagementImpl;

    @ApiOperation("根据id获取部门信息")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public CommonResult selectDepartmentById(@PathVariable Integer id) {
        return userManagementImpl.getUserById(id);
    }

    @ApiOperation("新增部门信息")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public CommonResult addDepartment(@RequestBody User user) {
        return userManagementImpl.insertUser(user);
    }

    @ApiOperation("修改部门信息")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public CommonResult updateDepartment(@RequestBody User user) {
        return userManagementImpl.updateUserById(user);
    }

    @ApiOperation("删除部门信息")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteDepartment(@PathVariable Integer id) {
        return userManagementImpl.deleteUserById(id);
    }

}
