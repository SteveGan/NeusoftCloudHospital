package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.LoginParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.OauthServiceImpl;

import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.SHAUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/oauth")
@CrossOrigin
public class OauthController {
    @Resource
    private OauthServiceImpl oauthServiceImpl;

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<JSONObject> login(@RequestBody LoginParam loginParam)
    {
        String pwd = SHAUtils.encodeData(loginParam.getPassword());
        JSONObject login = oauthServiceImpl.login(loginParam);
        return CommonResult.success(login) ;
    }

    @ApiOperation("返回用户所有角色信息")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public CommonResult<JSONObject> listAllUsersAndRoles()
    {
        JSONObject roles = oauthServiceImpl.listAllUsersAndRoles();
        return CommonResult.success(roles);
    }
}
