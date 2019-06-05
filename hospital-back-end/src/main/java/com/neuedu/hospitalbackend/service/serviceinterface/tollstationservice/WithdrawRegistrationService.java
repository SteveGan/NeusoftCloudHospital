package com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice;

import com.neuedu.hospitalbackend.model.po.*;
import com.neuedu.hospitalbackend.model.vo.DoctorParam;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.apache.ibatis.annotations.Case;

/**
 * 2.2 退号
 * @author Parachute.
 */
public interface WithdrawRegistrationService {

    /**
     * 通过患者病历号，显示患者挂号信息
     * @param registrationId
     * @return
     */
    public CommonResult getRegistrationInfo(Integer registrationId);

    /**
     *
     * @param registrationParam 挂号相关变量
     * @param patientCaseStatus 病历状态
     * @return
     */
    CommonResult operateTransactionLog(RegistrationParam registrationParam, Byte patientCaseStatus);

    /**
     * 向缴费表中添加新的缴费记录 -冲正
     * @param transactionLog 新的缴费记录
     * @return 冲正记录的发票号
     */


//    /**
//     * 从门诊病历首页移除该病历号，删除医生端的病历记录
//     * @param registrationParam 需要移除的病历号
//     */
//    CommonResult deletePatientCase(RegistrationParam registrationParam);

//    /**
//     * 更新所选医生对应的余号数量
//     * @param registrationParam 需要更新的医生
//     */
//    CommonResult updateRemainingAppointment(RegistrationParam registrationParam);

}
