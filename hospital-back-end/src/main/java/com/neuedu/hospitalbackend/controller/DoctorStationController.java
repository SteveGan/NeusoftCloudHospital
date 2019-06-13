package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.*;
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


    @ApiOperation("确诊诊断")
    @RequestMapping(value = "/finaldiagnose", method = RequestMethod.PUT)
    public CommonResult finalDiagnose(@RequestBody PatientCaseParam patientCaseParam)
    {
        return preliminaryCaseService.finalDiagnose(patientCaseParam);
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

    @ApiOperation("确诊诊断信息")
    @RequestMapping(value = "/finaldiagnose/{caseId}", method = RequestMethod.GET)
    public CommonResult listFinalDiagnose(@PathVariable(value = "caseId") Integer caseId)
    {
        return preliminaryCaseService.listFinalDiagnose(caseId);
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



    @ApiOperation("根据当前病历号，找到所有的项目申请单")
    @RequestMapping(value = "/collection/list/{caseId}/{type}", method = RequestMethod.GET)
    public CommonResult listCollections(@PathVariable(value = "caseId") Integer caseId,
                                        @PathVariable(value = "type") Integer type)
    {
        return projectCollectionManagementService.listCollections(caseId, type);
    }

    @ApiOperation("新增申请项目")
    @RequestMapping(value = "/collection/application/{collectionType}", method = RequestMethod.POST)
    public CommonResult applyNewCollection(@PathVariable Integer collectionType)
    {
        return projectCollectionManagementService.applyNewCollection(collectionType);
    }

    @ApiOperation("暂存申请项目")
    @RequestMapping(value = "/collection/preservation", method = RequestMethod.PUT)
    public CommonResult savePresentCollection(@RequestBody CollectionParam collectionParam)
    {
        return projectCollectionManagementService.savePresentCollection(collectionParam);
    }

    @ApiOperation("开立申请项目")
    @RequestMapping(value = "/collection/submission", method = RequestMethod.PUT)
    public CommonResult submitPresentCollection(@RequestBody CollectionParam collectionParam)
    {
        return projectCollectionManagementService.submitPresentCollection(collectionParam);
    }

    @ApiOperation("作废申请项目")
    @RequestMapping(value = "/collection/cancel/{collectionId}/{type}", method = RequestMethod.PUT)
    public CommonResult cancelSubmittedCollection(@PathVariable(value = "collectionId") Integer collectionId,
                                                  @PathVariable(value = "type") Integer type)
    {
        return projectCollectionManagementService.cancelSubmittedCollection(collectionId, type);
    }

    @ApiOperation("清除检查检验处置清单或处方")
    @RequestMapping(value = "/deletion/{collectionId}/{type}", method = RequestMethod.DELETE)
    public CommonResult deleteCollection(@PathVariable(value = "collectionId") Integer collectionId,
                                                  @PathVariable(value = "type") Integer type)
    {
        return projectCollectionManagementService.deleteCollection(collectionId, type);
    }


}
