package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.UserMapper;
import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.UserManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.SHAUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_602;

/**
 * @author Raven
 */
@Service
public class UserManagementImpl implements UserManagementService {
    @Resource
    UserMapper userMapper;

    @Override
    public CommonResult getUserById(Integer id) {
        User user = userMapper.get(id);
        return CommonResult.success(user);
    }

    @Override
    public CommonResult insertUser(User user) {
        // sha加密后密码
        String pwd = SHAUtils.encodeData(user.getPassword());
        user.setPassword(pwd);
        int count = userMapper.insert(user);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult updateUserById(User user) {
        if (userMapper.get(user.getId()) == null) {
            return CommonResult.fail(E_602);
        }
        int count = userMapper.update(user);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult deleteUserById(Integer id) {
        if (userMapper.get(id) == null) {
            return CommonResult.fail(E_602);
        }
        int count = userMapper.delete(id);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

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
            returnArray.add(jsonObject);
        }

        JSONObject roles = new JSONObject();
        roles.put("roles", returnArray);
        return roles;
    }
}
