package com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.vo.DoctorParam;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.model.po.PatientCase;
import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 2.1 现场挂号
 * @author Parachute
 */
public interface RegistrationService {

    /**
     * 返回当前的病历号
     * @return
     */
    CommonResult getNextRegistrationId();

    /**
     * 通过挂号科室id，返回该科室在指定时间、指定号别仍有余号的看诊医生列表
     * @param registrationParam 指定时间 指定号别 挂号科室id
     * @return 有余号的看诊医生列表(role_id, user_name, time_slot)
     */
    CommonResult listAvailableDoctors(RegistrationParam registrationParam);

    /**
     * 根据看诊医生和挂号级别，是否需要病历本，算出应收金额
     * @param registrationParam 号别  是否购买病历本
     * @return totalFee
     */
    CommonResult calculateTotalFee(RegistrationParam registrationParam);

    /**
     * 执行挂号操作
     * TODO：1.向缴费表中添加新的缴费记录  --已缴费
     * TODO: 2.向挂号表中添加新的挂号记录 --默认正常
     * TODO: 3.更新 所选医生 对应的余号数量
     * TODO: 4.检查患者是否已在本系统中
     * TODO: 5.向病历表中添加新的病历记录 --默认待诊
     * @param registrationParam 挂号记录
     * @return
     */
    CommonResult makeRegistration(RegistrationParam registrationParam);

    /**
     * 列出指定收费员的挂号记录
     * @param cashierId
     * @return
     */
    CommonResult listRegistrations(Integer cashierId);
}