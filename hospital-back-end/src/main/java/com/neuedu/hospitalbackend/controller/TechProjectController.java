package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
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
    public CommonResult listPatientByCaseIdOrName(ProjectPatientParam projectPatientParam)
    {
        return techProjectServiceImpl.listPreparedPatientsByCaseIdOrName(projectPatientParam);
    }

    @ApiOperation("选中患者，查看已申请的检查/检验项目详情")
    @RequestMapping(value = "/patient-projects", method = RequestMethod.POST)
    public CommonResult listAppliedProjectsByCaseId(@RequestBody ProjectPatientParam projectPatientParam){
        return techProjectServiceImpl.listAppliedProjectsByCaseId(projectPatientParam);
    }

    @ApiOperation("选中项目登记")
    @RequestMapping(value = "/project-checkin", method = RequestMethod.POST)
    public CommonResult checkInProject(@RequestBody ProjectParam projectParam){
        return techProjectServiceImpl.checkInProject(projectParam);
        // 后端再次确认项目状态 为 已缴费且未登记
    }

    @ApiOperation("选中项目取消登记")
    @RequestMapping(value = "/project-cancel", method = RequestMethod.POST)
    public CommonResult cancelProject(@RequestBody ProjectParam projectParam){
        return techProjectServiceImpl.cancelProject(projectParam);
        // 后端再次确认项目状态 为 已缴费且未登记
    }

    @ApiOperation("根据病历号，获取所有未录入结果项目列表")
    @RequestMapping(value = "/noresult", method = RequestMethod.POST)
    public CommonResult listCheckedInButNotRecordedProject(@RequestBody ProjectPatientParam projectPatientParam){
        return techProjectServiceImpl.listCheckedInButNotRecordedProject(projectPatientParam);
        // 后端再次确认项目状态 为 已缴费且未登记
    }

    @ApiOperation("录入项目结果")
    @RequestMapping(value = "/resultinput", method = RequestMethod.POST)
    public CommonResult recordResult(@RequestBody ProjectParam projectParam){
        return techProjectServiceImpl.recordResult(projectParam);
        // 后端再次确认项目状态 为 已缴费且未登记
    }

}