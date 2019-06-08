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
     * 通过患者病历号，查询收费项目
     * @param registrationId 病历号
     * @return 收费项目
     */
    CommonResult listTransactionLogs(Integer registrationId);

    /**
     * 更新收费项目的缴费状态
     * @param transactionLogList
     * @return
     */
    CommonResult updateTransactionLogsAsPaid(List<TransactionLog> transactionLogList);

//    /**
//     *  在检查/检验/处置/药品表查找想要退费的项目状态
//     * @param object 退费项目的collection_id, project_id
//     * @return 退费的项目
//     */
//    Object getItem(Object object);

    CommonResult updateTransactionLogsAsReturned(List<TransactionParam> transactionLogParams);
}
