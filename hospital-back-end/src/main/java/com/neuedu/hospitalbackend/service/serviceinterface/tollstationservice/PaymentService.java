package com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.model.vo.TransactionParam;
import com.neuedu.hospitalbackend.util.CommonResult;

import java.util.List;

/**
 * 2.3 收费 & 2.4 退费
 */
public interface PaymentService {

    /**
     * 通过患者病历号，查询收费项目及项目状态
     * @param registrationId 病历号
     * @return 收费项目
     */
    CommonResult listDetailedTransactionLogs(Integer registrationId);

    /**
     * 执行收费操作
     * @param transactionLogList
     * @return
     */
    CommonResult updateTransactionLogsAsPaid(List<TransactionLog> transactionLogList);

    /**
     * 执行退费操作
     * @param transactionLogParams
     * @return
     */
    CommonResult updateTransactionLogsAsReturned(List<TransactionParam> transactionLogParams);

    /**
     * 发票重打
     * @param invoiceCode
     * @return
     */
    CommonResult reprintTransactionLog(String invoiceCode, Integer newCashierId);

    /**
     * 输入患者病历号（必输）、开始时间和结束时间选填。查询该患者的所有收费项目列表
     * @param registrationId
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    CommonResult listTransactionLogsByIdAndDate(Integer registrationId, String beginDateStr, String endDateStr);
}
