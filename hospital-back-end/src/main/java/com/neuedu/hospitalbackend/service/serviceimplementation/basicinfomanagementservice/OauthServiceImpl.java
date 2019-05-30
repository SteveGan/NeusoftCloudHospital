package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dto.LoginParam;
import com.neuedu.hospitalbackend.model.daoback.RoleMapper;
import com.neuedu.hospitalbackend.model.daoback.UserMapper;
import com.neuedu.hospitalbackend.model.poback.Role;
import com.neuedu.hospitalbackend.model.poback.User;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.OauthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 身份验证服务
 */
@Service
public class OauthServiceImpl implements OauthService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public JSONObject login(LoginParam loginParam) {
        User user = userMapper.userAuthentication(loginParam.getUserId(), loginParam.getPassword());
        JSONObject result = new JSONObject();
        if(user != null){
            result.put("success",true);
            result.put("userId", user.getId());
            result.put("userName", user.getName());
            result.put("avatar", user.getAvatar());
            List<Role> roles = roleMapper.listRole(loginParam.getUserId());
            result.put("roles", roles);
            return result;
        } else{
            result.put("success", false);
        }
        return result;

    }
}
