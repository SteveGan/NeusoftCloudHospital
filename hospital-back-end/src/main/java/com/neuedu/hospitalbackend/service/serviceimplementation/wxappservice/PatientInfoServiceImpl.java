package com.neuedu.hospitalbackend.service.serviceimplementation.wxappservice;

import com.neuedu.hospitalbackend.model.dao.PatientMapper;
import com.neuedu.hospitalbackend.model.dao.RegistrationMapper;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.service.serviceinterface.wxappservice.PatientInfoService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Raven
 * @Date: 2019/6/11 5:54 PM
 */
@Service
public class PatientInfoServiceImpl implements PatientInfoService {
    @Resource
    private PatientMapper patientMapper;
    @Resource
    private RegistrationMapper registrationMapper;

    @Override
    public CommonResult getPatientInfo(Integer id) {
        Patient patient = patientMapper.getPatientInfoById(id);

        return CommonResult.success(patient);
    }

    @Override
    public CommonResult listRegistrationsByPatientId(Integer patientId) {
        List<HashMap> registrations = registrationMapper.listRegistrationsByPatientId(patientId);

        return CommonResult.success(registrations);
    }
}
