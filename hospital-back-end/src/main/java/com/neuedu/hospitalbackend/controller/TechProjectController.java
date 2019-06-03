package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.ProjectPatientParam;
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
    @RequestMapping(value = "/patient", method = RequestMethod.POST)
    public CommonResult<JSONObject> listPatientByCaseIdOrName(@RequestBody ProjectPatientParam projectPatientParam)
    {
        JSONObject patient = techProjectServiceImpl.listPreparedPatientsByCaseIdOrName(projectPatientParam);
        //TODO: ERRORCODE return null
        return CommonResult.success(patient);
    }

    @ApiOperation("选中患者，查看已申请的检查/检验项目详情")
    @RequestMapping(value = "/patient-projects", method = RequestMethod.POST)
    public CommonResult<JSONObject> listAppliedProjectsByCaseId(@RequestBody ProjectPatientParam projectPatientParam){
        JSONObject projects = techProjectServiceImpl.listAppliedProjectsByCaseId(projectPatientParam);
        //TODO: ERRORCODE return null
        return CommonResult.success(projects);
    }


}