package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.CheckDailyService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finance")
@CrossOrigin
public class CheckDailyController {

    @Autowired
    private CheckDailyService checkDailyService;

    @ApiOperation("门诊日结核对")
    @RequestMapping(value = "/dailysummary", method = RequestMethod.GET)
    public CommonResult checkDailySummary(DailySummaryParam dailySummaryParam){
        return checkDailyService.checkDailySummary(dailySummaryParam);
    }
}

