package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.DoctorStatisticsService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/doctor-statistics")
@CrossOrigin
public class DoctorStatisticsController {

    @Resource
    private DoctorStatisticsService doctorStatisticsService;

    @ApiOperation("所有临床医生工作量统计")
    @RequestMapping(value = "/clinician", method = RequestMethod.GET)
    public CommonResult clinicianDoctorsStatistics(String beginDateStr, String endDateStr){
        return doctorStatisticsService.doctorStatistics(beginDateStr, endDateStr);
    }

    @ApiOperation("所有临床医生工作量统计")
    @RequestMapping(value = "/clinician/{userId}", method = RequestMethod.GET)
    public CommonResult clinicianDoctorStatistics(@PathVariable(value = "userId") Integer userId, String dateStr){
        return doctorStatisticsService.doctorStatisticsByUserIdAndDate(dateStr, userId);
    }
}
