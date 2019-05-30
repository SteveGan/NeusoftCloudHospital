package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dto.DoctorParam;
import com.neuedu.hospitalbackend.model.dto.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice.RegistrationServiceImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tollstation")
@CrossOrigin
public class RegistrationController {
    
    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;

    @RequestMapping(value = "/arrangement-doctors", method = RequestMethod.GET)
    public CommonResult<JSONObject> listAvailableDoctors (@RequestBody RegistrationParam registrationParam){
        return CommonResult.success(registrationServiceImpl.listAvailableDoctors(registrationParam));
    }

    @RequestMapping(value = "/arrangement-appointment", method = RequestMethod.PATCH)
    public CommonResult<Integer> updateRemainingAppointment(@RequestBody DoctorParam doctorParam){
        int count = registrationServiceImpl.updateRemainingAppointment(doctorParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
