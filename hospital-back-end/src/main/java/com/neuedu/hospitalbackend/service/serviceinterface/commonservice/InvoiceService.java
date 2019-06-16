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
}
