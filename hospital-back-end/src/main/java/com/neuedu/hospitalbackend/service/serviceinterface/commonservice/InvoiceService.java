package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import com.neuedu.hospitalbackend.util.CommonResult;

public interface InvoiceService {

    /**
     * 返回可用的发票号
     * @return
     */
    CommonResult getNextInvoiceCode();
}
