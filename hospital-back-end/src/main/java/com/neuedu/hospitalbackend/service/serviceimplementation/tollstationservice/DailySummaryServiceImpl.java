package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.neuedu.hospitalbackend.model.dao.DailySummaryLogMapper;
import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.model.po.DailySummaryLog;
import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.DailySummaryService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.*;

@Component
public class DailySummaryServiceImpl implements DailySummaryService {

    @Autowired
    private TransactionLogMapper transactionLogMapper;
    @Autowired
    private DailySummaryLogMapper dailySummaryLogMapper;

    @Override
    public CommonResult getLastEndDate(Integer cashierId){
        String lastEndDate = dailySummaryLogMapper.getEndDateByCashierId(cashierId);
        if (lastEndDate == null){
            String firstLogDate = transactionLogMapper.getFirstLogDateByCashierId(cashierId);
            return CommonResult.success(firstLogDate);
        }
        else
            return CommonResult.success(lastEndDate);
    }

    @Override
    public CommonResult listLogsByCashierIdAndDate(DailySummaryParam dailySummaryParam){
        Integer cashierId = dailySummaryParam.getCashierId();
        String beginDateStr = dailySummaryParam.getBeginDateStr();
        String endDateStr = dailySummaryParam.getEndDateStr();
        List<HashMap> invoiceResult = transactionLogMapper.listLogsByCashierIdAndDate(cashierId, beginDateStr, endDateStr);
        if(invoiceResult.size()!=0)
            return CommonResult.success(invoiceResult);
        else
            return CommonResult.success(null);
    }

    @Override
    public CommonResult freezeTransactionLogs(DailySummaryParam dailySummaryParam){
        List<HashMap> transactionLogs = dailySummaryParam.getInvoiceCollection();
        Integer cashierRoleId = dailySummaryParam.getCashierId();
        Timestamp beginDate = Timestamp.valueOf(dailySummaryParam.getBeginDateStr());
        Timestamp endDate = Timestamp.valueOf(dailySummaryParam.getEndDateStr());
        BigDecimal totalMoney = dailySummaryParam.getTotalMoney();
        String invoiceCodeBegin = dailySummaryParam.getInvoiceCodeBegin();
        String invoiceCodeEnd = dailySummaryParam.getInvoiceCodeEnd();

        DailySummaryLog dailySummaryLog = new DailySummaryLog(cashierRoleId, totalMoney, invoiceCodeBegin,
                                                invoiceCodeEnd, beginDate, endDate);
        //更改收费记录状态 --冻结
        for (HashMap t: transactionLogs){
            transactionLogMapper.updateTransactionLogsByInvoiceCode((String) t.get("invoiceCode"));
        }

        //向日结表插入数据
        int count = dailySummaryLogMapper.insert(dailySummaryLog);
        if (count > 0)
            return CommonResult.success(count);
        else
            return CommonResult.fail(E_805);
    }

    @Override
    public CommonResult listHistorySummaryLogs(DailySummaryParam dailySummaryParam){
        Integer cashierId = dailySummaryParam.getCashierId();
        String beginDateStr = dailySummaryParam.getBeginDateStr();
        String endDateStr = dailySummaryParam.getEndDateStr();
        List<HashMap> dailySummaryLogs = dailySummaryLogMapper.getByCashierIdAndDate(cashierId, beginDateStr, endDateStr);
        if(dailySummaryLogs != null)
            return CommonResult.success(dailySummaryLogs);
        else
            return CommonResult.success(null);
    }

    @Override
    public CommonResult listInvoiceInfoByCashierIdAndDate(DailySummaryParam dailySummaryParam){
        Integer cashierId = dailySummaryParam.getCashierId();
        String createDateStr = dailySummaryParam.getCreateDateStr();
        HashMap invoiceRange = dailySummaryLogMapper.getInvoiceRangeByCashierIdAndDate(cashierId, createDateStr);
        String invoiceCodeBegin = (String) invoiceRange.get("invoiceCodeBegin");
        String invoiceCodeEnd = (String) invoiceRange.get("invoiceCodeEnd");
        List<HashMap> invoiceResult = transactionLogMapper.listLogsByInvoiceRangeAndCashierId(cashierId, invoiceCodeBegin, invoiceCodeEnd);
        return  CommonResult.success(invoiceResult);
    }

    @Override
    public CommonResult getFirstSummaryDate(Integer cashierId){
        String firstSummaryDate = dailySummaryLogMapper.getFirstSummaryDate(cashierId);
        if (firstSummaryDate == null)
            return CommonResult.success(null);
        else
            return CommonResult.success(firstSummaryDate);

    }
}
