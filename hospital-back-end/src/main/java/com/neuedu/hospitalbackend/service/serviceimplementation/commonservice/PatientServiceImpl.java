package com.neuedu.hospitalbackend.service.serviceimplementation.commonservice;

import com.neuedu.hospitalbackend.model.dao.PatientMapper;
import com.neuedu.hospitalbackend.model.dao.RegistrationMapper;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.neuedu.hospitalbackend.util.ResultCode.E_702;

@Service
public class PatientServiceImpl implements com.neuedu.hospitalbackend.service.serviceinterface.commonservice.PatientService {

    @Autowired
    private PatientMapper patientMapper;

    public CommonResult getPatientInfo(Integer registrationId) {
        Patient patient = patientMapper.getPatientByRegistrationId(registrationId);
        if(patient != null)
            return CommonResult.success(patient);
        else
            return CommonResult.fail(E_702);
    }
}


