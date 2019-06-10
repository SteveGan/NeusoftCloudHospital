package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.alibaba.fastjson.JSONObject;
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

    @ApiOperation("1.3.1 根据id获取用户信息")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public CommonResult getUserById(@PathVariable Integer id) {
        return userManagementImpl.getUserById(id);
    }

    @ApiOperation("1.3.2 新增用户信息")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public CommonResult addUser(@RequestBody User user) {
        return userManagementImpl.insertUser(user);
    }

    @ApiOperation("1.3.3 修改用户信息")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public CommonResult updateUser(@RequestBody User user) {
        return userManagementImpl.updateUserById(user);
    }

    @ApiOperation("1.3.4 删除用户信息")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteUser(@PathVariable Integer id) {
        return userManagementImpl.deleteUserById(id);
    }

    @ApiOperation("1.3.5 返回用户所有角色信息")
    @RequestMapping(value = "/users-with-roles", method = RequestMethod.GET)
    public CommonResult listAllUsersAndRoles()
    {
        return userManagementImpl.listAllUsersAndRoles();
    }
}
