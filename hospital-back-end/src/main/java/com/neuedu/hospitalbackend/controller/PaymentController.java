package com.neuedu.hospitalbackend.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.model.vo.TransactionParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.PatientService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.PaymentService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PaymentService paymentService;

    @ApiOperation("通过患者病历号，显示患者基本信息和收费项目详情")
    @RequestMapping(value = "/paymentInfo/{id}", method = RequestMethod.GET)
    public CommonResult getInfo(@PathVariable(value = "id") Integer registrationId){
        JSONObject jsonObject = new JSONObject();
        CommonResult patientResult = patientService.getPatientInfo(registrationId);
        if (patientResult.getCode() == 500)
            return patientResult;
        jsonObject.put("patientInfo", patientResult.getData());
        CommonResult paymentResult = paymentService.listDetailedTransactionLogs(registrationId);
        if (paymentResult.getCode() == 500)
            return paymentResult;
        jsonObject.put("paymentInfo", paymentResult.getData());
        return CommonResult.success(jsonObject);
    }

    @ApiOperation("更新选定收费项目的缴费状态 --已缴费")
    @RequestMapping(value = "/confirmation", method = RequestMethod.PUT)
    public CommonResult makePayment(@RequestBody List<TransactionLog> transactionLogs){
        return paymentService.updateTransactionLogsAsPaid(transactionLogs);
    }

    @ApiOperation("执行退费操作")
    @RequestMapping(value = "/withdrawal", method = RequestMethod.PUT)
    public CommonResult withdrawPayment(@RequestBody List<TransactionParam> transactionLogParams){
        return paymentService.updateTransactionLogsAsReturned(transactionLogParams);
    }

    @ApiOperation("发票重打")
    @RequestMapping(value = "/reprint/{invoiceCode}", method = RequestMethod.GET)
    public CommonResult reprintTransactionLog(@PathVariable(value = "invoiceCode") String invoiceCode, Integer newCashierId){
        return paymentService.reprintTransactionLog(invoiceCode, newCashierId);
    }

    @ApiOperation("患者费用查询")
    @RequestMapping(value = "/transactionLogs/{id}", method = RequestMethod.GET)
    public CommonResult reprintTransactionLog(@PathVariable(value = "id")Integer registrationId, String beginDateStr, String endDateStr){
        return paymentService.listLogsByRegistrationIdAndDate(registrationId, beginDateStr, endDateStr);
    }

}
