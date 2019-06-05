package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.LoginParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.OauthServiceImpl;

import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/oauth")
@CrossOrigin
public class OauthController {
    @Resource
    private OauthServiceImpl oauthServiceImpl;
    int count = 1;

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody LoginParam loginParam)
    {
        return oauthServiceImpl.login(loginParam);
    }

    @ApiOperation("返回用户所有角色信息")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public CommonResult<JSONObject> listAllUsersAndRoles()
    {
        JSONObject roles = oauthServiceImpl.listAllUsersAndRoles();
        return CommonResult.success(roles);
    }

    @ApiOperation("测试同步")
    @RequestMapping(value = "/sysynchronize", method = RequestMethod.GET)
    public String synchronize() throws InterruptedException {
        int a = 1;
        synchronized (this) {
            System.out.println("用户" + count + "正在访问~");
            Thread.sleep(5000);
            System.out.println("用户" + count + "离开~");
            count++;
        }
        return "success";
    }
}
