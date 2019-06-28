package com.neuedu.hospitalbackend.controller.basicinfomanagement;

import com.neuedu.hospitalbackend.model.vo.ArrangementParam;
import com.neuedu.hospitalbackend.model.vo.ArrangementRuleParam;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.ArrangementManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;

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

    @ApiOperation("生成排班结果")
    @RequestMapping(value = "/arrangement", method = RequestMethod.POST)
    public CommonResult insertArrangement(@RequestBody ArrangementParam arrangementParam) {
        return arrangementManagementService.insertArrangement(arrangementParam);
    }

    @ApiOperation("修改排班结果")
    @RequestMapping(value = "/arrangements/modification", method = RequestMethod.PUT)
    public CommonResult modifyArrangement(@RequestBody ArrangementParam arrangementParam) {
        return arrangementManagementService.modifyArrangement(arrangementParam);
    }

    @ApiOperation("查询排班规则")
    @RequestMapping(value = "/arrangementrules/{departmentId}", method = RequestMethod.GET)
    public CommonResult listArrangementRules(@PathVariable Integer departmentId) {
        return arrangementManagementService.listArrangementRules(departmentId);
    }

    @ApiOperation("查询排班结果")
    @RequestMapping(value = "/arrangements/{departmentId}", method = RequestMethod.GET)
    public CommonResult listArrangements(@PathVariable(value = "departmentId") Integer departmentId,
                                         @RequestParam Date startDate,
                                         @RequestParam Date endDate) {
        return arrangementManagementService.listArrangements(startDate, endDate, departmentId);
    }


}
