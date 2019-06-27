package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 获取可用发票号
 * @author Polaris
 */
public interface InvoiceService {

    /**
     * 返回可用的发票号
     * @return 执行结果
     */
    CommonResult getNextInvoiceCode();

    /**
     * 更新发票号的状态
     * @return 执行结果
     */
    CommonResult updateStatus(Byte status, String invoiceCode);

    /**
     * 插入发票
     * @param beginInvoiceCode 起始发票号
     * @param endInvoiceCode 终止发票号
     * @return 执行结果
     */
    CommonResult insertInvoices(String beginInvoiceCode, String endInvoiceCode);
}
