package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 1.3 用户管理
 */
public interface UserManagementService {

    /**
     * 1.2.1 根据用户id查询用户
     * @param id 用户id
     */
    CommonResult getUserById(Integer id);

    /**
     * 1.2.2 新增科室
     * @param user PO
     */
    CommonResult insertUser(User user);

    /**
     * 1.2.3 修改用户
     * @param user PO
     */
    CommonResult updateUserById(User user);

    /**
     * 1.2.4 删除用户
     * @param id 科室id
     */
    CommonResult deleteUserById(Integer id);
}
