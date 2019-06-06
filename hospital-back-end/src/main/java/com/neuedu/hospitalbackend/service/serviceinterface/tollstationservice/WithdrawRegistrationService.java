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
     * @return
     */
    CommonResult operateTransactionLog(RegistrationParam registrationParam);
}
