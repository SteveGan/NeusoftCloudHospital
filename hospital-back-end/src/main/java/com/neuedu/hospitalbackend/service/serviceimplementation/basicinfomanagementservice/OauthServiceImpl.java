package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dto.LoginParam;
import com.neuedu.hospitalbackend.model.dao.RoleMapper;
import com.neuedu.hospitalbackend.model.dao.UserMapper;
import com.neuedu.hospitalbackend.model.po.Role;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OauthServiceImpl implements OauthService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public JSONObject login(LoginParam loginParam) {
        int number = userMapper.getUserById(loginParam.getUserId(), loginParam.getPassword());
        JSONObject result = new JSONObject();
        if (number > 0) {
            List<Role> roles = roleMapper.listRole(loginParam.getUserId());
            for(Role role: roles) {
                System.out.println(role.getDepartmentName());
            }
            result.put("result", roles);
            return result;
        } else {
            result.put("result", "fail");
            return result;
        }
    }
}
