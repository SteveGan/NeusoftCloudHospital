package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.bo.Project;
import com.neuedu.hospitalbackend.model.po.Inspection;
import com.neuedu.hospitalbackend.model.vo.*;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.DiagnoseTemplateService;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.PatientCaseTemplateService;
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
    private PatientCaseTemplateService patientCaseTemplateService;
    @Resource
    private DiagnoseTemplateService diagnoseTemplateService;
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
    public CommonResult getPatientCaseInfo(@PathVariable(value = "doctorRoleId") Integer doctorRoleId,
                                           @PathVariable(value = "caseId") Integer caseId)
    {
        return preliminaryCaseService.getPatientCaseInfo(doctorRoleId, caseId);
    }

    @ApiOperation("暂存病历")
    @RequestMapping(value = "/preservation", method = RequestMethod.PUT)
    public CommonResult savePresentPatientCase(@RequestBody PatientCaseParam patientCaseParam)
    {
        return preliminaryCaseService.savePresentPatientCase(patientCaseParam);
    }

    @ApiOperation("提交病历")
    @RequestMapping(value = "/submission", method = RequestMethod.PUT)
    public CommonResult submitPresentPatientCase(@RequestBody PatientCaseParam patientCaseParam)
    {
        return preliminaryCaseService.submitPresentPatientCase(patientCaseParam);
    }

    @ApiOperation("清屏")
    @RequestMapping(value = "/clear/{id}", method = RequestMethod.DELETE)
    public CommonResult clearPatientCase(@PathVariable(value = "id") Integer caseId){
        return preliminaryCaseService.clearPatientCase(caseId);
    }


    @ApiOperation("存为病历模板")
    @RequestMapping(value = "/casetemplate/preservation", method = RequestMethod.POST)
    public CommonResult savePatientCaseTemplate(@RequestBody PatientCaseTemplateParam patientCaseTemplateParam)
    {
        return patientCaseTemplateService.saveAsCaseTemplate(patientCaseTemplateParam);
    }

    @ApiOperation("修改病历模板")
    @RequestMapping(value = "/casetemplate/modification", method = RequestMethod.PUT)
    public CommonResult modifyPatientCaseTemplate(@RequestBody PatientCaseTemplateParam patientCaseTemplateParam)
    {
        return patientCaseTemplateService.modifyPatientCaseTemplate(patientCaseTemplateParam);
    }

    @ApiOperation("显示病历模板")
    @RequestMapping(value = "/casetemplate/list/{id}", method = RequestMethod.GET)
    public CommonResult listPatientCaseTemplate(@PathVariable(value = "id") Integer roleId)
    {
        return patientCaseTemplateService.listPatientCaseTemplate(roleId);
    }

    @ApiOperation("删除病历模板")
    @RequestMapping(value = "/casetemplate/deletion/{roleId}/{caseTemplateName}", method = RequestMethod.DELETE)
    public CommonResult deletePatientCaseTemplate(@PathVariable(value = "roleId") Integer roleId,
                                                  @PathVariable(value = "caseTemplateName") String caseTemplateName)
    {
        return patientCaseTemplateService.deletePatientCaseTemplate(roleId, caseTemplateName);
    }

    @ApiOperation("创建常用诊断")
    @RequestMapping(value = "/diagnosetemplate/preservation", method = RequestMethod.POST)
    public CommonResult saveDiagnoseTemplate(@RequestBody DiagnoseTemplateParam diagnoseTemplateParam)
    {
        return diagnoseTemplateService.saveDiagnoseTemplate(diagnoseTemplateParam);
    }

    @ApiOperation("修改常用诊断")
    @RequestMapping(value = "/diagnosetemplate/modification", method = RequestMethod.PUT)
    public CommonResult modifyDiagnoseTemplate(@RequestBody DiagnoseTemplateParam diagnoseTemplateParam)
    {
        return diagnoseTemplateService.modifyDiagnoseTemplate(diagnoseTemplateParam);
    }

    @ApiOperation("查询常用诊断")
    @RequestMapping(value = "/diagnosetemplate/list/{roleId}", method = RequestMethod.GET)
    public CommonResult listMyDiagnoseTemplate(@PathVariable Integer roleId)
    {
        return diagnoseTemplateService.listMyDiagnoseTemplate(roleId);
    }

    //TODO 删除一个list
    @ApiOperation("删除常用诊断")
    @RequestMapping(value = "/diagnosetemplate/deletion/{roleId}/{templateId}", method = RequestMethod.DELETE)
    public CommonResult deleteMyDiagnoseTemplate(@PathVariable(value = "roleId") Integer roleId,
                                                 @PathVariable(value = "templateId") String diagnoseTemplateName)
    {
        return diagnoseTemplateService.deleteMyDiagnoseTemplate(roleId, diagnoseTemplateName);
    }


    @ApiOperation("申请检查项目")
    @RequestMapping(value = "/inspection/application", method = RequestMethod.POST)
    public CommonResult insertInspection(@RequestBody CollectionParam collectionParam)
    {
        return projectCollectionManagementService.insertCollection(collectionParam);
    }



}
