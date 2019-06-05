package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;

import com.neuedu.hospitalbackend.model.dao.InvoiceMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public synchronized CommonResult getNextInvoiceCode() {
        String nextInvoiceCode = invoiceMapper.getAvailableInvoiceCode();
        invoiceMapper.updateInvoiceStatusById(nextInvoiceCode);
        System.out.println("[INFO]正在使用: " + nextInvoiceCode);
        return CommonResult.success(nextInvoiceCode);
    }
}
