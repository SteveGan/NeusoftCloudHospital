package com.neuedu.hospitalbackend.controller;

import com.neuedu.hospitalbackend.model.vo.DiagnoseTemplateParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.DiagnoseTemplateService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/template")
@CrossOrigin
public class TemplateManagementController {


    @Resource
    private DiagnoseTemplateService diagnoseTemplateService;


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




}
