package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.ConstantManagementImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Raven
 * @Date: 2019/6/10 1:32 PM
 */
@RestController
@RequestMapping("/basicinfo")
@CrossOrigin
public class ConstantController {
    @Resource
    private ConstantManagementImpl constantManagement;
    @ApiOperation("1.2.1 树状图列出常量表")
    @RequestMapping(value = "/constants/tree", method = RequestMethod.GET)
    public CommonResult listConstantsTree() {
        return constantManagement.listConstantsTree();
    }
}
