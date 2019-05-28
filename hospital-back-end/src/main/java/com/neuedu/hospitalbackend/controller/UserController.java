package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.constant.ResponseCode;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.DepartmentManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private DepartmentManagement departmentManagement;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject register(HttpServletRequest request)
    {
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject login(HttpServletRequest request)
    {
        return null;
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
