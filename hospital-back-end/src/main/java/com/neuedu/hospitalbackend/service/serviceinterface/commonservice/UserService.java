package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * @author Steve
 */
public interface UserService {

    /**
     * 设置用户的头像
     * @param id 用户的id
     * @param avatarUrl 用户头像的链接
     * @return 结果
     */
    CommonResult setUserAvatar(Integer id, String avatarUrl);


    /**
     * 修改用户密码
     * @param userId 用户的id
     * @param oldPassword 用户的旧密码
     * @param newPassword 用户的新密码
     * @return 结果
     */
    CommonResult setUserPassword(Integer id, String oldPassword, String newPassword);
}
