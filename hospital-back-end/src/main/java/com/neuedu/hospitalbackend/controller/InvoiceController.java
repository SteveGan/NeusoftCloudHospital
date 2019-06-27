package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.DoctorStatisticsService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/invoice")
@CrossOrigin
public class InvoiceController {

    @Resource
    private InvoiceService invoiceService;

    @ApiOperation("根据号段，插入发票号")
    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public CommonResult insertInvoices(String beginInvoiceCode, String endInvoiceCode){
        return invoiceService.insertInvoices(beginInvoiceCode, endInvoiceCode);
    }
}
