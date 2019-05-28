package com.neuedu.hospitalbackend.service.serviceinterface.registrationservice;

import com.neuedu.hospitalbackend.model.po.ExceptionLog;
import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.model.po.TransactionLog;

public interface WithdrawRegistrationService {

    //2.2退号

    /**
     * 对原缴费记录进行操作
     * @param registration
     * @return 原缴费记录
     * TODO: 得到原缴费记录的流水号和金额, 将原有缴费记录状态更改为已退费
     */
    TransactionLog operateOriginalTransactionLog(Registration registration);

    /**
     * 向缴费表中添加新的缴费记录 -冲正
     * @param transactionLog 新的缴费记录
     * @return 冲正记录的发票号
     */
    Integer insertReverseTransactionLog(TransactionLog transactionLog);

    /**
     * 向异常表中添加新的记录
     * @param exceptionLog
     */
    void insertExceptionLog(ExceptionLog exceptionLog);




}
