package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.exception.UserLoginException;
import com.neuedu.hospitalbackend.exception.UserLoginInvalidException;
import com.neuedu.hospitalbackend.model.dao.RoleMapper;
import com.neuedu.hospitalbackend.model.dao.UserMapper;
import com.neuedu.hospitalbackend.model.po.Role;
import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.UserService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.JwtUtil;
import com.neuedu.hospitalbackend.util.SHAUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

import java.io.IOException;
import java.util.List;
import static com.neuedu.hospitalbackend.util.ResultCode.*;

/**
 * @author Steve
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;


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


    /**
     * 根据token查询用户基本信息及角色信息
     * @param token 用户的token
     */
    public CommonResult getUserInfo(String  token){
        try {
            Claims claims = JwtUtil.checkToken(token.substring(7));
            Integer userId = Integer.parseInt(claims.getSubject());
            User user = userMapper.getUserInfo(userId);
            JSONObject result = new JSONObject();
            if(user != null){
                result.put("userId", user.getId());
                result.put("userName", user.getName());
                result.put("avatar", user.getAvatar());
                List<Role> roles = roleMapper.listRole(userId);
                result.put("roles", roles);
                return CommonResult.success(result);
            } else{
                return CommonResult.fail(E_601);
            }
        }catch (UserLoginInvalidException e) {
            return CommonResult.fail(E_604);
        } catch (UserLoginException e) {
            return CommonResult.fail(E_603);
        } catch (IOException e){
            e.printStackTrace();
            return CommonResult.fail(E_603);
        }
    }


}
