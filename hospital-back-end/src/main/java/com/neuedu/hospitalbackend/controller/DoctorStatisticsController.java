package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.DoctorStatisticsService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/doctor-statistics")
@CrossOrigin
public class DoctorStatisticsController {

    @Resource
    private DoctorStatisticsService doctorStatisticsService;

    @ApiOperation("临床医生工作量统计")
    @RequestMapping(value = "/clinician", method = RequestMethod.GET)
    public CommonResult clinicianDepartmentStatistics(String beginDateStr, String endDateStr){
        return doctorStatisticsService.doctorStatistics(beginDateStr, endDateStr);
    }
}
