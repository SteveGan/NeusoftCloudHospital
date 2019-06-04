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
     * 对原缴费记录进行操作
     * @param registrationParam
     * @return 原缴费记录
     * TODO: 得到原缴费记录的流水号和金额, 将原有缴费记录状态更改为已退费
     */
    CommonResult operateOriginalTransactionLog(RegistrationParam registrationParam);

    /**
     * 向缴费表中添加新的缴费记录 -冲正
     * @param transactionLog 新的缴费记录
     * @return 冲正记录的发票号
     */


    /**
     * 从门诊病历首页移除该病历号，删除医生端的病历记录
     * @param patientCase 需要移除的病历号
     */
    CommonResult deleteCaseLog(PatientCase patientCase);

    /**
     * 更新所选医生对应的余号数量
     * @param doctorParam 需要更新的医生
     */
    CommonResult updateRemainingAppointment(DoctorParam doctorParam);

}
