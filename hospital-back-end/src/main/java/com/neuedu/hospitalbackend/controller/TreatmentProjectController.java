package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Treatment;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.model.vo.ProjectPatientParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice.TreatmentProjectServiceImpl;
import com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice.TreatmentProjectService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/treatment")
@CrossOrigin
public class TreatmentProjectController {
    @Resource
    private TreatmentProjectServiceImpl treatmentProjectServiceImpl;

    @ApiOperation("根据病历号或患者姓名，获取所有待登记患者列表")
    @RequestMapping(value = "/patient", method = RequestMethod.GET)
    public CommonResult listPatientByCaseIdOrName(@RequestBody ProjectPatientParam projectPatientParam)
    {
        JSONObject patient = treatmentProjectServiceImpl.listPreparedPatientsByCaseIdOrName(projectPatientParam);
        return CommonResult.success(patient);
    }

    @ApiOperation("选中患者，查看已申请的检查/检验项目详情")
    @RequestMapping(value = "/patient-projects", method = RequestMethod.POST)
    public CommonResult<JSONObject> listAppliedProjectsByCaseId(@RequestBody ProjectPatientParam projectPatientParam){
        JSONObject projects = treatmentProjectServiceImpl.listAppliedProjectsByCaseId(projectPatientParam);
        return CommonResult.success(projects);
    }

    @ApiOperation("选中项目登记")
    @RequestMapping(value = "/project-checkin", method = RequestMethod.POST)
    public CommonResult checkInProject(@RequestBody ProjectParam projectParam){
        int count = treatmentProjectServiceImpl.checkInProject(projectParam);
        // 后端再次确认项目状态 为 已缴费且未登记
        return CommonResult.success(count);
    }

    @ApiOperation("选中项目取消登记")
    @RequestMapping(value = "/project-cancel", method = RequestMethod.POST)
    public CommonResult cancelProject(@RequestBody ProjectParam projectParam){
        int count = treatmentProjectServiceImpl.cancelProject(projectParam);
        // 后端再次确认项目状态 为 已缴费且未登记
        return CommonResult.success(count);
    }


}
