package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.DailySummaryService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_708;

@Component
public class DailySummaryServiceImpl implements DailySummaryService {

    @Autowired
    private TransactionLogMapper transactionLogMapper;

    @Override
    public CommonResult listLogsByCashierIdAndDate(DailySummaryParam dailySummaryParam){
        List<TransactionLog> transactionLogs = transactionLogMapper.listLogsByCashierIdAndDate(dailySummaryParam.getCashierId(),
                dailySummaryParam.getBeginDateStr(), dailySummaryParam.getEndDateStr());
        if(transactionLogs.size()!=0)
            return CommonResult.success(transactionLogs);
        else
            return CommonResult.fail(E_708);
    }

    @Override
    public CommonResult freezeTransactionLogs(DailySummaryParam dailySummaryParam){
        for (TransactionLog transactionLog: dailySummaryParam.getTransactionLogs()){
            transactionLogMapper.freezeTransactionLogs(transactionLog.getInvoiceCode());
        }
        return CommonResult.fail();
    }
}
