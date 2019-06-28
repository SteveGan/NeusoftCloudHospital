package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;

import com.neuedu.hospitalbackend.model.dao.InvoiceMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Date;
import java.text.SimpleDateFormat;

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
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String currentDay = sdf.format(new Date());*/
        String nextInvoiceCode = invoiceMapper.getAvailableInvoiceCode(null);
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

    @Override
    public CommonResult insertInvoices(String beginInvoiceCode, String endInvoiceCode){
        BigDecimal begin = new BigDecimal(beginInvoiceCode);
        BigDecimal end = new BigDecimal(endInvoiceCode);
        int count = 0;
        while(begin.compareTo(end) != 1){
            count += invoiceMapper.insert(begin.toString());
            begin = begin.add(BigDecimal.valueOf(1));
        }
        if(count > 0)
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }
}
