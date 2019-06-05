package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.PatientParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.PreliminaryCaseService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/doctorstation")
@CrossOrigin
public class DoctorStationController {

    @Resource
    private PreliminaryCaseService preliminaryCaseService;

    @ApiOperation("根据病历号或患者姓名，获取所有待诊患者列表")
    @RequestMapping(value = "/patient", method = RequestMethod.GET)
    public CommonResult listPatientByCaseIdOrName(PatientParam patientParam)
    {
//        return preliminaryCaseService.listWaitingPatients(patientParam);
        return CommonResult.fail();
    }

}
