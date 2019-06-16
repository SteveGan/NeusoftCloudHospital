package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.model.vo.ArrangementParam;
import com.neuedu.hospitalbackend.model.vo.ArrangementRuleParam;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.ArrangementManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/basicinfo")
@CrossOrigin
public class ArrangementManagementController {

    @Resource
    ArrangementManagementService arrangementManagementService;

    @ApiOperation("设定排班规则")
    @RequestMapping(value = "/arrangementrule", method = RequestMethod.POST)
    public CommonResult insertArrangementRule(@RequestBody ArrangementRuleParam arrangementRuleParam) {
        return arrangementManagementService.insertArrangementRule(arrangementRuleParam);
    }

    @ApiOperation("生成排班规则")
    @RequestMapping(value = "/arrangement", method = RequestMethod.POST)
    public CommonResult insertArrangement(@RequestBody ArrangementParam arrangementParam) {
        return arrangementManagementService.insertArrangement(arrangementParam);
    }



}
