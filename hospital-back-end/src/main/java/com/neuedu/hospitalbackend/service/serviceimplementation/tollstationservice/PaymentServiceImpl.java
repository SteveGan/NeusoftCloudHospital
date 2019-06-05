package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.PaymentService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_700;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionLogMapper transactionLogMapper;

    public CommonResult listTransactionLogs(Integer registrationId){
        List<TransactionLog> transactionLogList = transactionLogMapper.listTransactionLogs(registrationId);
        return CommonResult.success(transactionLogList);
    }

    public CommonResult updateTransactionLogs(List<TransactionLog> transactionLogList){
        int count = 0;
        for (TransactionLog transactionLog: transactionLogList){
            transactionLog.setStatus((byte)2);
            count += transactionLogMapper.updateSelective(transactionLog);
        }
        if (count != transactionLogList.size())
            return CommonResult.fail(E_700);
        return CommonResult.success(count);
    }
}
