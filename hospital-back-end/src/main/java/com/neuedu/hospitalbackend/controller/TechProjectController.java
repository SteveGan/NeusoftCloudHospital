package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.ProjectCheckInParam;
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
    @RequestMapping(value = "/patient", method = RequestMethod.GET)
    public CommonResult<JSONObject> listPatientByCaseIdOrName(@RequestBody ProjectPatientParam projectPatientParam)
    {
        JSONObject patient = techProjectServiceImpl.listPreparedPatientsByCaseIdOrName(projectPatientParam);
        return CommonResult.success(patient);
    }

    @ApiOperation("选中患者，查看已申请的检查/检验项目详情")
    @RequestMapping(value = "/patient-projects", method = RequestMethod.POST)
    public CommonResult<JSONObject> listAppliedProjectsByCaseId(@RequestBody ProjectPatientParam projectPatientParam){
        JSONObject projects = techProjectServiceImpl.listAppliedProjectsByCaseId(projectPatientParam);
        return CommonResult.success(projects);
    }

    @ApiOperation("选中项目登记")
    @RequestMapping(value = "/project-checkin", method = RequestMethod.POST)
    public CommonResult checkInProject(@RequestBody ProjectCheckInParam projectCheckInParam){
        int result = techProjectServiceImpl.checkInProject(projectCheckInParam);
        //TODO: 后端确认项目状态为 已缴费且未登记
        return CommonResult.success(result);
    }

}