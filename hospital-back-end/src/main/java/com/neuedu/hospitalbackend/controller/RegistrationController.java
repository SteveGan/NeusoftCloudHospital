package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.DoctorParam;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice.RegistrationServiceImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@CrossOrigin
public class RegistrationController {
    
    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;

    @ApiOperation("查看所有可选医生")
    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public CommonResult<JSONObject> listAvailableDoctors (RegistrationParam registrationParam) {
        JSONObject availableDoctors = registrationServiceImpl.listAvailableDoctors(registrationParam);
        return CommonResult.success(availableDoctors);
    }


    @ApiOperation("计算总金额")
    @RequestMapping(value = "/appointment", method = RequestMethod.PATCH)
    public CommonResult<JSONObject> calculateTotalFee(@RequestBody RegistrationParam registrationParam){
        JSONObject totalFee = registrationServiceImpl.calculateTotalFee(registrationParam);
        return CommonResult.success(totalFee);
    }



}
