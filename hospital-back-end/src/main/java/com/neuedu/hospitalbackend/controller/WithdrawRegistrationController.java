package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.commonservice.PatientServiceImpl;
import com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice.WithdrawRegistrationServiceImpl;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.PatientService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.WithdrawRegistrationService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration-withdrawal")
@CrossOrigin
public class WithdrawRegistrationController {

    @Autowired
    private WithdrawRegistrationService withdrawRegistrationService = new WithdrawRegistrationServiceImpl();

    @Autowired
    private PatientService patientService = new PatientServiceImpl();

    @ApiOperation("通过患者病历号，显示患者基本信息和挂号信息")
    @RequestMapping(value = "/registrationInfo", method = RequestMethod.GET)
    public CommonResult getRegistrationInfo(Integer registrationId){
        JSONObject jsonObject = new JSONObject();
        Patient patientInfo = (Patient) patientService.getPatientInfo(registrationId).getData();
        if (patientInfo != null) {
            jsonObject.put("patientInfo", patientInfo);
            Registration registrationInfo = (Registration) withdrawRegistrationService.getRegistrationInfo(registrationId).getData();
            if (registrationInfo != null){
                jsonObject.put("registrationInfo", registrationInfo);
                return CommonResult.success(jsonObject);
            }
        }
        return CommonResult.fail();
    }

    @ApiOperation("执行退号操作")
    @RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
    public CommonResult makeRegistrationWithdrawal(RegistrationParam registrationParam, Byte patientCaseStatus){
        CommonResult result = withdrawRegistrationService.operateTransactionLog(registrationParam, patientCaseStatus);
        if(result.getCode() == 500)
            return CommonResult.fail();
        return result;
    }

}

