package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.CheckDailyService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/finance")
@CrossOrigin
public class CheckDailyController {

    @Resource
    private CheckDailyService checkDailyService;

    @ApiOperation("门诊日结核对")
    @RequestMapping(value = "/dailysummary", method = RequestMethod.GET)
    public CommonResult checkDailySummary(DailySummaryParam dailySummaryParam){
        return checkDailyService.checkDailySummary(dailySummaryParam);
    }

    @ApiOperation("核对通过")
    @RequestMapping(value = "/confirm", method = RequestMethod.PUT)
    public CommonResult confirmResult(@RequestBody DailySummaryParam dailySummaryParam){
        return checkDailyService.confirmResult(dailySummaryParam);
    }
}

