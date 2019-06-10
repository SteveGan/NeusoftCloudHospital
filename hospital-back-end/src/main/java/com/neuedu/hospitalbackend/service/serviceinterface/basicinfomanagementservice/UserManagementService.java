package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 1.3 用户管理
 */
public interface UserManagementService {

    /**
     * 1.3.1 根据用户id查询用户
     * @param id 用户id
     */
    CommonResult getUserById(Integer id);

    /**
     * 1.3.2 新增用户
     * @param user PO
     */
    CommonResult insertUser(User user);

    /**
     * 1.3.3 修改用户
     * @param user PO
     */
    CommonResult updateUserById(User user);

    /**
     * 1.3.4 删除用户
     * @param id 用户id
     */
    CommonResult deleteUserById(Integer id);

    /**
     * 1.3.5 返回所有用户以及角色信息
     */
    CommonResult listAllUsersAndRoles();
}
