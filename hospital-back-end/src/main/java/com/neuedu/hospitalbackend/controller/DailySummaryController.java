package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.DailySummaryService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dailysummary")
@CrossOrigin
public class DailySummaryController {

    @Autowired
    private DailySummaryService dailySummaryService;

    @ApiOperation("查询收费员上次日结截止时间")
    @RequestMapping(value = "/lastdate", method = RequestMethod.GET)
    public CommonResult getLastEndDate(Integer cashierId){
        return dailySummaryService.getLastEndDate(cashierId);
    }


    @ApiOperation("收费员日结统计")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public CommonResult conductDailyTransactionLogs(DailySummaryParam dailySummaryParam){
        return dailySummaryService.listLogsByCashierIdAndDate(dailySummaryParam);
    }

    @ApiOperation("收费员结算报账")
    @RequestMapping(value = "/settlement", method = RequestMethod.PUT)
    public CommonResult freezeDailyTransactionLogs(@RequestBody DailySummaryParam dailySummaryParam){
        return dailySummaryService.freezeTransactionLogs(dailySummaryParam);
    }

    @ApiOperation("查询收费员第一次日结的时间")
    @RequestMapping(value = "/firstsummarydate", method = RequestMethod.GET)
    public CommonResult getFirstSummaryDate(Integer cashierId){
        return dailySummaryService.getFirstSummaryDate(cashierId);
    }

    @ApiOperation("收费员日结历史查询")
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public CommonResult listHistorySummaryLogs(DailySummaryParam dailySummaryParam){
        return dailySummaryService.listHistorySummaryLogs(dailySummaryParam);
    }

    @ApiOperation("根据日结信息，显示其对应的发票信息")
    @RequestMapping(value = "/invoiceinfo", method = RequestMethod.GET)
    public CommonResult listInvoiceResults(DailySummaryParam dailySummaryParam){
        return dailySummaryService.listInvoiceInfoByCashierIdAndDate(dailySummaryParam);
    }

}
