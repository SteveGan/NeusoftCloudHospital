package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Diagnose;
import com.neuedu.hospitalbackend.model.po.Patient;

import java.util.List;


/**
 * 包含了门诊确诊需要使用的服务函数
 * @author Steve
 */
public interface FinalCaseService {

    /**
     * 得到该医生的所有已诊患者
     * @param doctorId of the doctor
     * @return list of patient
     */
    public List<Patient> listDiagnosedPatients(Integer doctorId);


    /**
     * 暂存当前最终诊断结果
     * @param obj 当前最终诊断的内容
     */
    public void saveFinalCase(JSONObject obj);


    /**
     * 提交当前最终诊断结果，注意：这是最终版本
     * @param obj 当前最终诊断的内容
     */
    public void submitFinalCase(JSONObject obj);



}
