package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.component.LoginLog;
import com.neuedu.hospitalbackend.model.dao.LogLoginMapper;
import com.neuedu.hospitalbackend.model.po.LogLogin;
import com.neuedu.hospitalbackend.model.vo.LoginParam;
import com.neuedu.hospitalbackend.model.dao.RoleMapper;
import com.neuedu.hospitalbackend.model.dao.UserMapper;
import com.neuedu.hospitalbackend.model.po.Role;
import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.OauthService;
import com.neuedu.hospitalbackend.util.AddressUtils;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.JwtUtil;
import com.neuedu.hospitalbackend.util.SHAUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.neuedu.hospitalbackend.constant.Cache.hospitalLogger;
import static com.neuedu.hospitalbackend.util.ResultCode.E_601;


/**
 * 身份验证服务
 * @author Raven
 */
@Service
public class OauthServiceImpl implements OauthService {


    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Autowired
    private LoginLog loginLog;

    @Override
    public CommonResult login(LoginParam loginParam, HttpServletRequest request) {
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

            String token = JwtUtil.getToken(user.getId().toString());

            result.put("web_token", "Bearer:" + token);
            System.out.println();
            hospitalLogger.info("登录成功: " + loginParam.getUserId() + " with token: " + token);
            //异步记录登录操作
            loginLog.run(loginParam, request);

            return CommonResult.success(result);
        } else{
            hospitalLogger.info("登录失败: " + loginParam.getUserId());
            return CommonResult.fail(E_601);
        }
    }


}
