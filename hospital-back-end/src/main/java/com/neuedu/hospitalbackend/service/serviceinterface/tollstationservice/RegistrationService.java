package com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dto.RegistrationParam;
import com.neuedu.hospitalbackend.model.po.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * 2.1 现场挂号
 * @author Parachute
 */
public interface RegistrationService {

    /**
     * 检查患者是否已在本系统中
     * @param IdCard 患者身份证号
     * @return the patient
     * TODO: 如返回值不为空，传patient_id到前端；如返回值为空，则插入新记录到patient表
     */
    Patient getPatient(String IdCard);

    /**
     * 通过挂号科室id，返回该科室在指定时间、指定号别仍有余号的看诊医生列表
     * @param registrationParam 指定时间 指定号别 挂号科室id
     * @return 有余号的看诊医生列表
     */
    JSONObject listAvailableDoctors(RegistrationParam registrationParam);

    /**
     * 更新所选医生对应的余号数量
     * @param arrangement 需要更新的医生
     */
    void updateRemainingAppointment(Arrangement arrangement);

    /**
     * 根据看诊医生和挂号级别，是否需要病历本，算出应收金额
     * @param jsonObject 看诊医生职称，挂号级别
     * @return total_amount of a registration
     */
    int calculateAmount(JSONObject jsonObject);

    /**
     * 向挂号表中添加新的挂号记录 --默认正常
     * @param registration
     */
    void insertRegistrationLog(Registration registration);

    /**
     * 向病历表中添加新的病历记录 --默认待诊
     * @param patientCase
     */
    void insertPatientCaseLog(PatientCase patientCase);
}
