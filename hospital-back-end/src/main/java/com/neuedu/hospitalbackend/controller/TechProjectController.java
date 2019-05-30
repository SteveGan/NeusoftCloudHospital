package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dto.ProjectPatientParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice.TechProjectServiceImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/techproject")
@CrossOrigin
public class TechProjectController {
    @Resource
    private TechProjectServiceImpl techProjectServiceImpl;

    @ApiOperation("根据病历号或患者姓名，获取所有待登记患者列表")
    @RequestMapping(value = "/patients", method = RequestMethod.POST)
    public CommonResult<JSONObject> listPatientByCaseIdOrName(@RequestBody ProjectPatientParam projectPatientParam)
    {
        JSONObject patient = techProjectServiceImpl.listPreparedPatientsByCaseIdOrName(projectPatientParam);
        return CommonResult.success(patient);
    }

}