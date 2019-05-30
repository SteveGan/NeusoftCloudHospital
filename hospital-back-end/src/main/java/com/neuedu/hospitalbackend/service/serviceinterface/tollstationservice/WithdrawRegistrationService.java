package com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice;

import com.neuedu.hospitalbackend.model.poback.*;
import org.apache.ibatis.annotations.Case;

/**
 * 2.2 退号
 * @author Parachute.
 */
public interface WithdrawRegistrationService {

    /**
     * 对原缴费记录进行操作
     * @param registration
     * @return 原缴费记录
     * TODO: 得到原缴费记录的流水号和金额, 将原有缴费记录状态更改为已退费
     */
    TransactionLog operateOriginalTransactionLog(Registration registration);

    /**
     * 向缴费表中添加新的缴费记录 -冲正
     * @param transactionLog 新的缴费记录
     * @return 冲正记录的发票号
     */


    /**
     * 从门诊病历首页移除该病历号，删除医生端的病历记录
     * @param c 需要移除的病历号
     */
    void deleteCaseLog(Case c);

    /**
     * 更新所选医生对应的余号数量
     * @param arrangement 需要更新的医生
     */
    void updateRemainingAppointment(Arrangement arrangement);

}
