package com.neuedu.hospitalbackend.service.serviceinterface.wxappservice;

import com.neuedu.hospitalbackend.util.CommonResult;

/**@author Raven
 * @Date 2019/6/11 5:54 PM
 */
public interface PatientInfoService {

    CommonResult getPatientInfo(Integer id);

    CommonResult listRegistrationsByPatientId(Integer patientId);

    /**
     *
     * @param caseId 病历号
     * @return 执行结果
     */
    CommonResult getPatientResultInfo(Integer caseId);

    /**
     * 列出指定患者前方等待人数
     * @param registrationId 病历号
     * @return 执行结果
     */
    CommonResult getWaitingAmountById(Integer registrationId);
}
