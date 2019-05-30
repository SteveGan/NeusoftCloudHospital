package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dto.DoctorParam;
import com.neuedu.hospitalbackend.model.dto.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;

@RestController
@RequestMapping("/tollstation")
@CrossOrigin
public class RegistrationController {
    
    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;

    @RequestMapping(value = "/arrangement-doctors", method = RequestMethod.GET)
    public JSONObject listAvailableDoctors (@RequestBody RegistrationParam registrationParam){
        return registrationServiceImpl.listAvailableDoctors(registrationParam);
    }

    @RequestMapping(value = "/arrangement-appointment", method = RequestMethod.PATCH)
    public JSONObject updateRemainingAppointment(@RequestBody DoctorParam doctorParam){
        registrationServiceImpl.updateRemainingAppointment(doctorParam);
        return null;
    }



}
