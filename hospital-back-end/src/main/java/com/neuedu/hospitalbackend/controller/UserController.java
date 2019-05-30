package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dto.LoginParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.DepartmentManagement;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.OauthServiceImpl;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Resource
    private DepartmentManagement departmentManagement;
    @Resource
    private OauthServiceImpl oauthServiceImpl;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject register(HttpServletRequest request)
    {
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject login(@RequestBody LoginParam loginParam)
    {
        return oauthServiceImpl.login(loginParam);
    }

    /**
     * 返回所有用户，包括role信息
     * @return
     */
    @RequestMapping(value = "/listall", method = RequestMethod.GET)
    public JSONArray listAllUsersAndRoles()
    {
        return oauthServiceImpl.listAllUsersAndRoles();
    }

    private JSONObject response(Object data, String code, String msg) {
        return getJsonObject(data, code, msg);
    }

    static JSONObject getJsonObject(Object data, String code, String msg) {
        JSONObject result = new JSONObject();
        result.put("data", data);
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }
}
