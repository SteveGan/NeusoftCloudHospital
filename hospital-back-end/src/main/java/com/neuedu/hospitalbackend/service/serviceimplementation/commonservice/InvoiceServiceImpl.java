package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;

import com.neuedu.hospitalbackend.model.dao.InvoiceMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.neuedu.hospitalbackend.util.ResultCode.E_713;

/**
 * @author Polaris
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    @Override
    public synchronized CommonResult getNextInvoiceCode() {
        String nextInvoiceCode = invoiceMapper.getAvailableInvoiceCode();
        if(nextInvoiceCode == null)
            return CommonResult.fail(E_713);
        invoiceMapper.updateInvoiceStatusById((byte)2, nextInvoiceCode);
        System.out.println("[INFO]正在使用发票号: " + nextInvoiceCode);
        return CommonResult.success(nextInvoiceCode);
    }

    @Override
    public CommonResult updateStatus(Byte status, String invoiceCode){
        int count = invoiceMapper.updateInvoiceStatusById(status, invoiceCode);
        if (count > 0)
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }
}
