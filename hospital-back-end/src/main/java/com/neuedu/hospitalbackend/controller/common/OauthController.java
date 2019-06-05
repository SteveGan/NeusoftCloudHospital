package com.neuedu.hospitalbackend.controller.common;

import com.neuedu.hospitalbackend.constant.Cache;
import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.model.vo.LoginParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.UserManagementImpl;
import com.neuedu.hospitalbackend.service.serviceimplementation.commonservice.OauthServiceImpl;

import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Raven
 */
@RestController
@RequestMapping("/oauth")
@CrossOrigin
public class OauthController {
    @Resource
    private OauthServiceImpl oauthServiceImpl;
    @Resource
    private UserManagementImpl userManagementImpl;

    private int count = 1;

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody LoginParam loginParam, HttpServletRequest httpServletRequest)
    {
        return oauthServiceImpl.login(loginParam, httpServletRequest);
    }

    @ApiOperation("注册")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public CommonResult register(@RequestBody User user) {
        return userManagementImpl.insertUser(user);
    }

    @ApiOperation("测试同步关键字")
    @RequestMapping(value = "/sysynchronize", method = RequestMethod.GET)
    public CommonResult<String> synchronize() throws InterruptedException {
        synchronized (this) {
            Cache.hospitalLogger.info("用户" + count + "正在访问~");
            Thread.sleep(5000);
            Cache.hospitalLogger.info("用户" + count + "离开~");
            count++;
        }
        return CommonResult.success("success");
    }
}
