package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.util.CommonResult;

import java.util.List;

public interface PatientService{


    /**
     * 通过患者病历号，显示患者挂号信息
     * @param patientCaseId
     * @return
     */
    CommonResult getPatientInfo(Integer patientCaseId);

}
