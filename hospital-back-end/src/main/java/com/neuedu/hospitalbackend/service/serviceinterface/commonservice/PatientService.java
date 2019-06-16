package com.neuedu.hospitalbackend.service.serviceinterface.commonservice;

import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 查询患者的相关信息
 * @author Polaris
 */
public interface PatientService{


    /**
     * 通过患者病历号，显示患者基本信息
     * @param patientCaseId 患者病历号
     * @return 执行结果
     */
    CommonResult getPatientInfo(Integer patientCaseId);

    /**
     * 通过患者病历号，显示患者挂号信息
     * @param registrationId 患者病历号
     * @return 执行结果
     */
    CommonResult getRegistrationInfo(Integer registrationId);
}
