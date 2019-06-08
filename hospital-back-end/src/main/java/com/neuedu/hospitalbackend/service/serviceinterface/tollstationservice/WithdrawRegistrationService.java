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
     * 执行退号操作
     * TODO: 1.向缴费表中添加新的缴费记录  --冲正
     * TODO: 2.将原有缴费记录状态更改为已退费 --已退费
     * TODO: 3.向异常表中添加新的记录
     * TODO: 4.在挂号表中更新该病历号的状态 --已退号
     * TODO: 5.从病历表中移除病历记录
     * TODO: 6.增加 所选医生 的余号数量
     * @param registrationParam
     * @return
     */
    CommonResult operateTransactionLog(RegistrationParam registrationParam);
}
