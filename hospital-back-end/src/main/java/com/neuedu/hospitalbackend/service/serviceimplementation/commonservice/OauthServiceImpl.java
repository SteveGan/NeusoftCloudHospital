package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.LoginParam;
import com.neuedu.hospitalbackend.model.dao.RoleMapper;
import com.neuedu.hospitalbackend.model.dao.UserMapper;
import com.neuedu.hospitalbackend.model.po.Role;
import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.OauthService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.JwtUtil;
import com.neuedu.hospitalbackend.util.SHAUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_601;


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
    public CommonResult login(LoginParam loginParam) {
        // sha加密后密码
        String pwd = SHAUtils.encodeData(loginParam.getPassword());
        loginParam.setPassword(pwd);
        User user = userMapper.userAuthentication(loginParam.getUserId(), loginParam.getPassword());

        JSONObject result = new JSONObject();
        if(user != null){
            result.put("userId", user.getId());
            result.put("userName", user.getName());
            result.put("avatar", user.getAvatar());
            List<Role> roles = roleMapper.listRole(loginParam.getUserId());
            result.put("roles", roles);
            String token = JwtUtil.getToken(user.getName());
            result.put("web_token", "Bearer:" + token);
            System.out.println(token + "      " + loginParam.getUserId());
            return CommonResult.success(result);
        } else{
            return CommonResult.fail(E_601);
        }
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
            returnArray.add(jsonObject);
        }

        JSONObject roles = new JSONObject();
        roles.put("roles", returnArray);
        return roles;
    }
}
