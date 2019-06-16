package com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice;

import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 退号
 * 当患者由于挂错了号或者是挂了号没有看诊，此时可以通过退号的操作来完成取消挂号，
 * 退还患者费用后，收回患者发票，同时打印发票收据（也叫冲红发票，相应金额为负值），
 * 收费人员把两张发票订在一起，金额相互抵消
 * @author Polaris
 */
public interface WithdrawRegistrationService {

    /**
     * 执行退号操作
     * @param registrationParam: registrationId, patientCaseStatus, appointmentDate...
     * @return 操作结果
     */
    CommonResult operateTransactionLog(RegistrationParam registrationParam);
}
