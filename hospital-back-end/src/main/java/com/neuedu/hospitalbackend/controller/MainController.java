package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.constant.ResponseCode;
import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.service.BasicInformationManagement;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/his")
public class MainController {
    @Resource
    private BasicInformationManagement basicInformationManagement;

    @RequestMapping("/selectDepartmentById")
    public JSONObject selectDepartmentById(String id)
    {
        Department department = new Department();
        department.setId(Integer.valueOf(id));
        return response(basicInformationManagement.selectDepartmentById(department), ResponseCode.RESPONSE_CODE_OK, ResponseCode.RESPONSE_MSG_OK);
    }

    private JSONObject response(Object data, String code, String msg) {
        JSONObject result = new JSONObject();
        result.put("data", data);
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }
}
