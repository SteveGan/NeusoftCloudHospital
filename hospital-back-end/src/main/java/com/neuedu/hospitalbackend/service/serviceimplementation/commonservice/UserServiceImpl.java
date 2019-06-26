package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;


import com.neuedu.hospitalbackend.model.dao.UserMapper;
import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.UserService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.SHAUtils;
import org.apache.log4j.net.SocketHubAppender;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

import static com.neuedu.hospitalbackend.util.ResultCode.*;

/**
 * @author Steve
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    /**
     * 设置用户的头像
     * @param id 用户的id
     * @param avatarUrl 用户头像的链接
     * @return 结果
     */
    public CommonResult setUserAvatar(Integer id, String avatarUrl){

        User user = userMapper.get(id);
        //如果用户不存在
        if (user == null){
            return CommonResult.fail(E_600);
        }

        userMapper.setAvatar(id, avatarUrl);
        return CommonResult.success(null);
    }


    /**
     * 修改用户密码
     * @param userId 用户的id
     * @param oldPassword 用户的旧密码
     * @param newPassword 用户的新密码
     * @return 结果
     */
    public CommonResult setUserPassword(Integer userId, String oldPassword, String newPassword){

        String encryptedOldPass = SHAUtils.encodeData(oldPassword);
        String encryptedNewPass = SHAUtils.encodeData(newPassword);

        //判断用户是否存在
        if (userMapper.get(userId) == null){
            return CommonResult.fail(E_600);
        }

        //判断原密码是否正确
        if (userMapper.userAuthentication(userId, encryptedOldPass) == null){
            return CommonResult.fail(E_606);
        }

        //判断新密码与旧密码是否相同
        if (encryptedNewPass == null || encryptedNewPass.equals(encryptedOldPass)){
            return CommonResult.fail(E_607);
        }

        //设置新密码
        userMapper.setPassword(userId, encryptedNewPass);
        return CommonResult.success(null);
    }


}
