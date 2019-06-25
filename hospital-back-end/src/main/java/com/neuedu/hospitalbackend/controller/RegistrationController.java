package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.RegistrationService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/registration")
@CrossOrigin
public class RegistrationController {
    
    @Resource
    private RegistrationService registrationService;

    @Resource
    private InvoiceService invoiceService;

    @ApiOperation("得到当前挂号单的病历号")
    @RequestMapping(value = "/registrationId", method = RequestMethod.GET)
    public CommonResult getNextRegistrationId(){
        return registrationService.getNextRegistrationId();
    }

    @ApiOperation("得到当前挂号单的发票号")
    @RequestMapping(value = "/invoiceId", method = RequestMethod.GET)
    public CommonResult getNextInvoiceCode(){
        return invoiceService.getNextInvoiceCode();
    }

    @ApiOperation("查看所有可选医生")
    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public CommonResult listAvailableDoctors (RegistrationParam registrationParam) {
        return registrationService.listAvailableDoctors(registrationParam);
    }

    @ApiOperation("根据挂号级别，是否购买病历本，计算应收金额")
    @RequestMapping(value = "/totalFee", method = RequestMethod.GET)
    public CommonResult calculateTotalFee(RegistrationParam registrationParam){
        return registrationService.calculateTotalFee(registrationParam);
    }

    @ApiOperation("挂号操作")
    @RequestMapping(value = "/confirmation", method = RequestMethod.POST)
    public CommonResult makeRegistration(@RequestBody RegistrationParam registrationParam){
        return registrationService.makeRegistration(registrationParam);
    }

    @ApiOperation("显示所有挂号信息列表")
    @RequestMapping(value = "/registrations", method = RequestMethod.GET)
    public CommonResult listRegistrations(){
        return registrationService.listRegistrations();
    }

    @ApiOperation("恢复未用发票号状态")
    @RequestMapping(value = "/{invoiceCode}", method = RequestMethod.PUT)
    public CommonResult restoreInvoiceStatus(@PathVariable(value = "invoiceCode")String invoiceCode){
        return invoiceService.updateStatus((byte)1, invoiceCode);
    }
}
