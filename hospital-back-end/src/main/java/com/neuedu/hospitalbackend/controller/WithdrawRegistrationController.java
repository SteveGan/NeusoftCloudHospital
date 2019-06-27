package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.PatientService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.WithdrawRegistrationService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/registration-withdrawal")
@CrossOrigin
public class WithdrawRegistrationController {

    @Resource
    private WithdrawRegistrationService withdrawRegistrationService;

    @Resource
    private PatientService patientService;

    @ApiOperation("通过患者病历号，显示患者基本信息和挂号信息")
    @RequestMapping(value = "/registrationInfo/{id}", method = RequestMethod.GET)
    public CommonResult getInfo(@PathVariable(value = "id") Integer registrationId){
       return patientService.getRegistrationInfo(registrationId);
    }

    @ApiOperation("执行退号操作")
    @RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
    public CommonResult makeRegistrationWithdrawal(@RequestBody RegistrationParam registrationParam){
        return withdrawRegistrationService.operateTransactionLog(registrationParam);
    }
}

