package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.constant.ResponseCode;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.DepartmentManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * controller路径名 == controller方法名 ==
 */
@RestController
@RequestMapping("/his")
public class MainController {
    @Resource
    private DepartmentManagement departmentManagement;

    @RequestMapping("/selectDepartmentById")
    public JSONObject selectDepartmentById(String id)
    {
        return response(departmentManagement.selectDepartmentById(id), ResponseCode.RESPONSE_CODE_OK, ResponseCode.RESPONSE_MSG_OK);
    }

    @RequestMapping("/selectDepartmentByNameAndCode")
    public JSONObject selectDepartmentByNameAndCode(JSONObject obj)
    {
        return response(departmentManagement.selectDepartmentByNameAndCode(obj), ResponseCode.RESPONSE_CODE_OK, ResponseCode.RESPONSE_MSG_OK);
    }

    private JSONObject response(Object data, String code, String msg) {
        JSONObject result = new JSONObject();
        result.put("data", data);
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }
}
