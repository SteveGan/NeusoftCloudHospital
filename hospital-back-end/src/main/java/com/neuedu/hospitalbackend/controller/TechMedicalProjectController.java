package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.model.vo.PatientParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice.TechMedicalProjectServiceImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/techproject")
@CrossOrigin
public class TechMedicalProjectController {

    @Resource
    private TechMedicalProjectServiceImpl techMedicalProjectServiceImpl;

    @ApiOperation("（根据病历号或患者姓名或收费日期）获取所有等待列表")
    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public CommonResult listPatientByCaseIdOrName(PatientParam patientParam)
    {
        return techMedicalProjectServiceImpl.listPreparedPatientsByCaseIdOrDateOrName(patientParam);
    }

    @ApiOperation("选中患者，查看已申请的检查/检验项目")
    @RequestMapping(value = "/patient-projects", method = RequestMethod.GET)
    public CommonResult listAllProjectsByCaseId(PatientParam patientParam){
        return techMedicalProjectServiceImpl.listAllProjectsByCaseId(patientParam);
    }

    @ApiOperation("选中患者的某个项目，查看项目详情")
    @RequestMapping(value = "/patient-detailedprojects", method = RequestMethod.GET)
    public CommonResult listItemsByCollectionIdAndProjectId(ProjectParam projectParam){
        return techMedicalProjectServiceImpl.listItemsByCollectionIdAndProjectId(projectParam);
    }

    @ApiOperation("选中项目登记")
    @RequestMapping(value = "/checkin", method = RequestMethod.PUT)
    public CommonResult checkInProject(@RequestBody ProjectParam projectParam){
        System.out.println("1111" + projectParam.getCollectionId());
        System.out.println("2222" + projectParam.getDoctorRoleId());
        System.out.println("3333" + projectParam.getTransactionLogStatus());
        System.out.println("4444" + projectParam.getProjectStatus());
        System.out.println("5555" + projectParam.getProjectId());
        return techMedicalProjectServiceImpl.checkInProject(projectParam);
    }

    @ApiOperation("选中项目取消登记")
    @RequestMapping(value = "/cancellation", method = RequestMethod.PUT)
    public CommonResult cancelProject(@RequestBody ProjectParam projectParam){
        return techMedicalProjectServiceImpl.cancelProject(projectParam);
    }

    @ApiOperation("显示本科室已登记项目列表")
    @RequestMapping(value = "/registeredprojects", method = RequestMethod.GET)
    public CommonResult listCheckedInButNotRecordedProject(ProjectParam projectParam){
        return techMedicalProjectServiceImpl.listCheckedInButNotRecordedProjects(projectParam);
    }

    @ApiOperation("录入项目结果")
    @RequestMapping(value = "/result", method = RequestMethod.PUT)
    public CommonResult recordResult(@RequestBody ProjectParam projectParam){
        return techMedicalProjectServiceImpl.recordResult(projectParam);
    }

    @ApiOperation("确定某项目执行完毕")
    @RequestMapping(value = "/confirmation", method = RequestMethod.PUT)
    public CommonResult confirmProject(@RequestBody ProjectParam projectParam){
        return techMedicalProjectServiceImpl.confirmProject(projectParam);
    }
}