package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dto.LoginParam;
import com.neuedu.hospitalbackend.model.dao.RoleMapper;
import com.neuedu.hospitalbackend.model.dao.UserMapper;
import com.neuedu.hospitalbackend.model.po.Role;
import com.neuedu.hospitalbackend.model.po.User;
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

    /**
     * 所有用户以及角色信息
     * @return jsonArray:[user:{ id,role:[] }, ]
     */
    @Override
    public JSONObject listAllUsersAndRoles(){
        JSONArray returnArray = new JSONArray();

        List<User> users = userMapper.listAllUsersAndRoles();

        for(User user : users){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", user.getId());
            jsonObject.put("userName", user.getName());
            jsonObject.put("userAvatar", user.getAvatar());
            jsonObject.put("userCreateTime",user.getGmtCreate());
            jsonObject.put("roles",user.getRoles());
            returnArray.add(jsonObject);
        }

        JSONObject roles = new JSONObject();
        roles.put("roles", returnArray);
        return roles;
    }
}
