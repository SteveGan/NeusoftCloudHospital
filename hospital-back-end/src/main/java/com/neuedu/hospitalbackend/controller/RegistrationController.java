package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
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

    @RequestMapping(value = "/availabledoctors", method = RequestMethod.POST)
    public JSONObject listAvailableDoctors (@RequestBody RegistrationParam registrationParam){
        //registrationParam.setAppointmentDate(Date.valueOf(registrationParam.getAppointmentDateStr()));
        return registrationServiceImpl.listAvailableDoctors(registrationParam);
    }







}
