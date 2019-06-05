package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.DoctorParam;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice.RegistrationServiceImpl;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.RegistrationService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@CrossOrigin
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationServiceImpl;

    @Autowired
    private InvoiceService invoiceService;

    @ApiOperation("得到当前挂号单的病历号")
    @RequestMapping(value = "/registrationId", method = RequestMethod.GET)
    public CommonResult getNextRegistrationId(){
        return registrationServiceImpl.getNextRegistrationId();
    }

    @ApiOperation("得到当前挂号单的发票号")
    @RequestMapping(value = "/invoiceId", method = RequestMethod.GET)
    public CommonResult getNextInvoiceCode(){
        return invoiceService.getNextInvoiceCode();
    }

    @ApiOperation("查看所有可选医生")
    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public CommonResult listAvailableDoctors (RegistrationParam registrationParam) {
        return registrationServiceImpl.listAvailableDoctors(registrationParam);
    }

    @ApiOperation("根据挂号级别，是否购买病历本，计算应收金额")
    @RequestMapping(value = "/totalFee", method = RequestMethod.GET)
    public CommonResult calculateTotalFee(RegistrationParam registrationParam){
        return registrationServiceImpl.calculateTotalFee(registrationParam);
    }

    @ApiOperation("挂号操作")
    @RequestMapping(value = "/confirmation", method = RequestMethod.POST)
    public CommonResult makeRegistration(@RequestBody RegistrationParam registrationParam){
        return registrationServiceImpl.makeRegistration(registrationParam);
    }


}
