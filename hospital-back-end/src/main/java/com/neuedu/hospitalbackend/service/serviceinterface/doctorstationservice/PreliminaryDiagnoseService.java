package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Patient;

import java.util.List;


/**
 * 包含关于门诊病历首页的service方法
 * @author Steve
 */
public interface PreliminaryDiagnoseService {


    /**
     * get all  waited patient of the doctor
     * @param doctorId of the doctor
     * @return list of patient
     */
    public List<Patient> getWaitedPatients(Integer doctorId);


    /**
     * 得到该医生的所有已诊患者
     * @param doctorId of the doctor
     * @return list of patient
     */
    public List<Patient> getDiagnosedPatients(Integer doctorId);


    /**
     * 将当前病历首页中的内容存入数据库
     * @param caseInfo 当前病历首页中的内容
     */
    public void storeCurrentPreCase(JSONObject caseInfo);





}
