package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.DailySummaryService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dailysummary")
@CrossOrigin
public class DailySummaryController {

    @Autowired
    private DailySummaryService dailySummaryService;

    @ApiOperation("收费员日结统计")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public CommonResult conductDailyTransactionLogs(DailySummaryParam dailySummaryParam){
        return dailySummaryService.listLogsByCashierIdAndDate(dailySummaryParam);
    }

    @ApiOperation("收费员结算报账")
    @RequestMapping(value = "/settlement", method = RequestMethod.GET)
    public CommonResult freezeDailyTransactionLogs(DailySummaryParam dailySummaryParam){
        return dailySummaryService.freezeTransactionLogs(dailySummaryParam);
    }

    @ApiOperation("收费员日结历史查询")
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public CommonResult listHistorySummaryLogs(DailySummaryParam dailySummaryParam){
        return dailySummaryService.listHistorySummaryLogs(dailySummaryParam);
    }

}
