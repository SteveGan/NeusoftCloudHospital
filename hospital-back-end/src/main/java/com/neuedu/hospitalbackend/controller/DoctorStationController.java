package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.PatientCaseParam;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.PreliminaryCaseService;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.ProjectCollectionManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/doctorstation")
@CrossOrigin
public class DoctorStationController {

    @Resource
    private PreliminaryCaseService preliminaryCaseService;
    @Resource
    private ProjectCollectionManagementService projectCollectionManagementService;

    @ApiOperation("获取所有待诊、已诊患者列表")
    @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
    public CommonResult listPatients(@PathVariable(value = "id") Integer doctorRoleId)
    {
        return preliminaryCaseService.listPatients(doctorRoleId);
    }

    @ApiOperation("点击患者，查看病历首页")
    @RequestMapping(value = "/patientcaseinfo/{doctorRoleId}/{caseId}", method = RequestMethod.GET)
    public CommonResult getPatientCaseInfo(@PathVariable(value = "doctorRoleId") Integer doctorRoleId, @PathVariable(value = "caseId") Integer caseId)
    {
        return preliminaryCaseService.getPatientCaseInfo(doctorRoleId, caseId);
    }

    //TODO 测试
    @ApiOperation("暂存病历")
    @RequestMapping(value = "/preservation", method = RequestMethod.PUT)
    public CommonResult savePresentPatientCase(PatientCaseParam patientCaseParam)
    {
        return preliminaryCaseService.savePresentPatientCase(patientCaseParam);
    }

    //TODO 测试
    @ApiOperation("提交病历")
    @RequestMapping(value = "/submission", method = RequestMethod.PUT)
    public CommonResult submitPresentPatientCase(PatientCaseParam patientCaseParam)
    {
        return preliminaryCaseService.submitPresentPatientCase(patientCaseParam);
    }

    //TODO 测试
    @ApiOperation("清屏")
    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public CommonResult clearPatientCase(Integer caseId){
        return preliminaryCaseService.clearPatientCase(caseId);
    }

    //TODO 测试 + iml
    @ApiOperation("病历存为模板")
    @RequestMapping(value = "/casetemplate/preservation", method = RequestMethod.POST)
    public CommonResult savePatientCaseTemplate(PatientCaseParam patientCaseParam)
    {
        return preliminaryCaseService.submitPresentPatientCase(patientCaseParam);
    }

    //TODO 测试
    @ApiOperation("申请检查项目")
    @RequestMapping(value = "/inspection-application", method = RequestMethod.POST)
    public CommonResult insertInspection(ProjectParam projectParam)
    {
        return projectCollectionManagementService.insertProjectCollection(projectParam);
    }

}
