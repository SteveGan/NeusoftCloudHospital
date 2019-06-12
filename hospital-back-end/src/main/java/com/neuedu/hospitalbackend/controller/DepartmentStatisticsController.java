package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.DepartmentStatisticsService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department-statistics")
@CrossOrigin
public class DepartmentStatisticsController {

    @Autowired
    private DepartmentStatisticsService departmentStatisticsService;

    @ApiOperation("临床科室工作量统计")
    @RequestMapping(value = "/clinician", method = RequestMethod.GET)
    public CommonResult clinicianDepartmentStatistics(String beginDateStr, String endDateStr){
        return departmentStatisticsService.clinicianDepartmentStatistics(beginDateStr, endDateStr);
    }

    @ApiOperation("医技科室工作量统计")
    @RequestMapping(value = "/technicianD", method = RequestMethod.GET)
    public CommonResult technicianDepartmentStatistics(String beginDateStr, String endDateStr){
        return departmentStatisticsService.technicianDepartmentStatistics(beginDateStr, endDateStr);
    }
}
