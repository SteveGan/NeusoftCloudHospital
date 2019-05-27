package com.neuedu.hospitalbackend.service.serviceinterface.registrationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Arrangement;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegistrationService {

    /**
     * 检查患者是否已在本系统中
     * @param IdCard 患者身份证号
     * @return the patient
     */
    Patient queryPatient(String IdCard);

    /**
     * 通过挂号科室id，返回该科室在指定时间、指定号别仍有余号的看诊医生列表
     * @param object 挂号科室id 指定时间 指定号别
     * @return 有余号的看诊医生列表
     */
    List<User> listAvailableDoctors(JSONObject object);

    /**
     * 更新所选医生对应的余号数量
     * @param user 需要更新的医生
     */
    void updateRemainingAppointment(User user);

    /**
     *
     * @param object 看诊医生职称，挂号级别
     * @return total_amount of a registration
     */
    int calculateAmount(JSONObject object);



}
