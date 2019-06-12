package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.CostManagementImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Raven
 * @Date: 2019/6/12 11:13 AM
 */
@RestController
@RequestMapping("/basicinfo")
@CrossOrigin
public class CostManagementController {
    @Resource
    private CostManagementImpl costManagementImpl;

    @ApiOperation("列出全部科目")
    @RequestMapping(value = "/costs", method = RequestMethod.GET)
    public CommonResult listAllCosts() {
        return costManagementImpl.listAllCosts();
    }
}
