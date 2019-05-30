package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.constant.ResponseCode;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.DepartmentManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.neuedu.hospitalbackend.controller.UserController.getJsonObject;

/**
 * controller路径名 == controller方法名 ==
 * @author all
 */
@RestController
@RequestMapping("/his")
@CrossOrigin
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
}
