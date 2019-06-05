package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;

import com.neuedu.hospitalbackend.model.dao.TransactionExceptionLogMapper;
import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.model.po.TransactionExceptionLog;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.neuedu.hospitalbackend.util.ResultCode.E_605;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionLogMapper transactionLogMapper;
    @Autowired
    private TransactionExceptionLogMapper transactionExceptionLogMapper;

    public CommonResult insertTransactionExceptionLog(String originalCode, String newCode, String reverseCode, Integer roleId, String reason){
        TransactionExceptionLog transactionExceptionLog = new TransactionExceptionLog();
        transactionExceptionLog.setOriginalInvoiceCode(originalCode);
        transactionExceptionLog.setNewInvoiceCode(newCode);
        transactionExceptionLog.setReverseInvoiceCode(reverseCode);
        transactionExceptionLog.setRoleId(roleId);
        transactionExceptionLog.setReason(reason);
        int count = transactionExceptionLogMapper.insertSelective(transactionExceptionLog);
        if(count == 0)
            return CommonResult.fail(E_605);
        return CommonResult.success(transactionExceptionLog);
    }

    public CommonResult insertTransactionLog(String invoiceCode, Integer registrationId, Integer patientId, Integer roleId,
                                             String type, Integer collectionId, Integer projectId, Integer itemId,
                                             Short amount, Byte payType, BigDecimal totalMoney, Byte status){
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setInvoiceCode(invoiceCode);
        transactionLog.setRegistrationId(registrationId);
        transactionLog.setPatientId(patientId);
        transactionLog.setRoleId(roleId);
        transactionLog.setType(type);
        transactionLog.setCollectionId(collectionId);
        transactionLog.setProjectId(projectId);
        transactionLog.setItemId(itemId);
        transactionLog.setAmount(amount);
        transactionLog.setPayType(payType);
        transactionLog.setTotalMoney(totalMoney);
        transactionLog.setStatus(status);
        int count = transactionLogMapper.insertSelective(transactionLog);
        if(count == 0)
            return CommonResult.fail(E_605);
        return CommonResult.success(transactionLog);
    }

}
