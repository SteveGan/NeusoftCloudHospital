package com.neuedu.hospitalbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dto.ProjectPatientParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice.TechProjectServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/techProject")
@CrossOrigin
public class TechProjectController {
    @Resource
    private TechProjectServiceImpl techProjectServiceImpl;

    /**
     * 根据病历号或患者姓名，获取所有待登记患者列表
     * @param projectPatientParam: caseId, patientName
     * @return 患者信息列表
     */
    @RequestMapping(value = "/patients", method = RequestMethod.POST)
    public JSONObject listPatientByCaseIdOrName(@RequestBody ProjectPatientParam projectPatientParam)
    {
        return techProjectServiceImpl.listPreparedPatientsByCaseIdOrName(projectPatientParam);
    }

}