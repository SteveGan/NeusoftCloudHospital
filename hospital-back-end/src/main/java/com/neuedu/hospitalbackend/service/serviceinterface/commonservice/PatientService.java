package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import com.neuedu.hospitalbackend.util.CommonResult;

public interface PatientService{


    /**
     * 通过患者病历号，显示患者基本信息
     * @param patientCaseId
     * @return
     */
    CommonResult getPatientInfo(Integer patientCaseId);

    /**
     * 通过患者病历号，显示患者挂号信息
     * @param registrationId
     * @return
     */
    CommonResult getRegistrationInfo(Integer registrationId);
}
