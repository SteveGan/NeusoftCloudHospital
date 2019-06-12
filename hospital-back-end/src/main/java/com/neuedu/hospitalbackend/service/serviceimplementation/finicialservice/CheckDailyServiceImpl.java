package com.neuedu.hospitalbackend.service.serviceimplementation.finicialservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.DailySummaryLogMapper;
import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.model.po.DailySummaryLog;
import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.CheckDailyService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CheckDailyServiceImpl implements CheckDailyService {

    @Autowired
    private DailySummaryLogMapper dailySummaryLogMapper;
    @Autowired
    private TransactionLogMapper transactionLogMapper;

    public CommonResult checkDailySummary(DailySummaryParam dailySummaryParam){
        JSONObject jsonObject = new JSONObject();
        Integer cashierId = dailySummaryParam.getCashierId();
        String beginDateStr = dailySummaryParam.getBeginDateStr();
        String endDateStr = dailySummaryParam.getEndDateStr();
        DailySummaryLog dailySummaryLog = dailySummaryLogMapper.getSummaryLog(cashierId, beginDateStr, endDateStr);
        String invoiceCodeBegin = dailySummaryLog.getInvoiceCodeBegin();
        String invoiceCodeEnd = dailySummaryLog.getInvoiceCodeEnd();
        for(int i = 1; i <= 5; i++) {
            List<String> invoiceCollection = transactionLogMapper.listInvoiceCodeByStatus(cashierId, invoiceCodeBegin, invoiceCodeEnd, (byte) i);
            jsonObject.put(String.valueOf(i), invoiceCollection);
        }
        List<HashMap> summary = transactionLogMapper.listTotalMoneyByType(cashierId, invoiceCodeBegin, invoiceCodeEnd);
        for(HashMap s: summary){
            jsonObject.put((String) s.get("type"), s.get("total_money"));
        }
        return CommonResult.success(jsonObject);
    }
}
