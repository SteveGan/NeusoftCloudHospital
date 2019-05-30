package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import com.neuedu.hospitalbackend.model.bo.ExceptionLog;
import com.neuedu.hospitalbackend.model.po.TransactionLog;

import java.util.List;

public interface TransactionService {

    /**
     * 更新指定项目的缴费状态
     * @param transactionLog
     */
    void updateTransactionLog(TransactionLog transactionLog);

    /**
     * 向缴费表中添加新的缴费记录 缴费/冲正
     * @param transactionLog 新的缴费记录
     * @return 新的发票号
     */
    Integer insertTransactionLog(TransactionLog transactionLog);

    /**
     * 向异常表中添加新的记录
     * @param exceptionLog
     */
    void insertExceptionLog(ExceptionLog exceptionLog);

    /**
     * 根据原发票号，查询缴费信息
     * @param invoiceCode
     * @return 发票号一致的项目列表
     */
    List<Object> listTransacitionLogsByInvoiceCode(Integer invoiceCode);
}
