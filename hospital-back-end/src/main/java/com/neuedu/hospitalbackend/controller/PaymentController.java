package com.neuedu.hospitalbackend.controller;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.PatientService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.PaymentService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PaymentService paymentService;

    @ApiOperation("通过患者病历号，显示患者基本信息和收费项目列表")
    @RequestMapping(value = "/registrationInfo", method = RequestMethod.GET)
    public CommonResult getInfo(Integer registrationId){
        JSONObject jsonObject = new JSONObject();
        Patient patientInfo = (Patient) patientService.getPatientInfo(registrationId).getData();
        if (patientInfo != null) {
            List<TransactionLog> transactionLogs = (List) paymentService.listTransactionLogs(registrationId);
            jsonObject.put("transactionLogs", transactionLogs);
            return CommonResult.success(jsonObject);
        }
        return CommonResult.fail();
    }






}
