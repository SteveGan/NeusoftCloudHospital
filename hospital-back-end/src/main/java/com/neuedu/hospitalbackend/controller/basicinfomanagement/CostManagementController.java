package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.model.po.Cost;
import com.neuedu.hospitalbackend.model.po.NonMedicine;
import com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice.CostManagementImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("根据id获取科目信息")
    @RequestMapping(value = "/cost/{id}", method = RequestMethod.GET)
    public CommonResult getCostById(@PathVariable Integer id) {
        return costManagementImpl.getCostById(id);
    }

    @ApiOperation("新增科目信息")
    @RequestMapping(value = "/cost", method = RequestMethod.POST)
    public CommonResult addCost(@RequestBody Cost cost) {
        return costManagementImpl.insertCost(cost);
    }


}
