package com.neuedu.hospitalbackend.controller.wxappcontroller;

import com.neuedu.hospitalbackend.service.serviceimplementation.wxappservice.PatientInfoServiceImpl;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用于显示患者相关信息，按照列表、详情方式来查询可用于扫码查询
 * @author Raven
 * @Date: 2019/6/9 7:28 PM
 */
@RestController
@RequestMapping("/patient")
@CrossOrigin
public class PatientInfoController {
    @Resource
    private PatientInfoServiceImpl patientInfoServiceImpl;

    @ApiOperation("获取患者信息")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public CommonResult getPatientInfo(@PathVariable(value = "id") Integer patientId)
    {
        return patientInfoServiceImpl.getPatientInfo(patientId);
    }

    @ApiOperation("获取患者挂号列表")
    @RequestMapping(value = "/registrations/{id}", method = RequestMethod.GET)
    public CommonResult listPatientRegistrations(@PathVariable(value = "id") Integer patientId)
    {
        return patientInfoServiceImpl.listRegistrationsByPatientId(patientId);
    }

    @ApiOperation("获取患者挂号详细信息，包括前方等待人数")
    @RequestMapping(value = "/registration/{id}", method = RequestMethod.GET)
    public CommonResult getPatientRegistration(@PathVariable(value = "id") Integer patientId)
    {
        return patientInfoServiceImpl.getWaitingAmountById(patientId);
    }

    @ApiOperation("获取患者病历信息列表")
    @RequestMapping(value = "/patient-cases/{id}", method = RequestMethod.GET)
    public CommonResult listPatientCases(@PathVariable(value = "id") Integer patientId)
    {
        return null;
    }

    @ApiOperation("获取患者病历详细信息: 门诊医生初诊记录，医技医生检查结果、处方药品详细信息")
    @RequestMapping(value = "/patient-case/{id}", method = RequestMethod.GET)
    public CommonResult getPatientCase(@PathVariable(value = "id") Integer caseId)
    {
        return null;
    }

    @ApiOperation("获取患者缴费信息列表，以病历为单位")
    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.GET)
    public CommonResult listPatientTransactions(@PathVariable(value = "id") Integer patientId)
    {
        return null;
    }

    @ApiOperation("获取患者缴费详细信息")
    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.GET)
    public CommonResult getPatientTransaction(@PathVariable(value = "id") Integer transactionId)
    {
        return null;
    }

    @ApiOperation("获取患者个人统计信息，就诊次数，偏爱医生，患病提示")
    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    public CommonResult getPatientData(@PathVariable(value = "id") Integer patientId)
    {
        return null;
    }

    @ApiOperation("获取患者吃药提醒，药品不足提醒")
    @RequestMapping(value = "/notification/{id}", method = RequestMethod.GET)
    public CommonResult getPatientNotification(@PathVariable(value = "id") Integer patientId)
    {
        return null;
    }

    @ApiOperation("获取患者本次看诊病历、检查、检验、处置、处方信息")
    @RequestMapping(value = "/resultinfo/{id}", method = RequestMethod.GET)
    public CommonResult getPatientResultInfo(@PathVariable(value = "id") Integer caseId)
    {
        return patientInfoServiceImpl.getPatientResultInfo(caseId);
    }
}
