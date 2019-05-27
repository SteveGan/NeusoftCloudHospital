package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.PatientCaseTemplate;

import java.util.List;


/**
 * 5.1 门诊病历首页
 * 包含关于门诊病历首页的service方法
 * @author Steve
 */
public interface PreliminaryDiagnoseService {


    /**
     * get all  waited patient of the doctor
     * @param doctorId of the doctor
     * @return list of patient
     */
    public List<Patient> listWaitedPatients(Integer doctorId);


    /**
     * 得到该医生的所有已诊患者
     * @param doctorId of the doctor
     * @return list of patient
     */
    public List<Patient> listDiagnosedPatients(Integer doctorId);


    /**
     * 将当前病历首页中的内容存入数据库
     * @param caseInfo 当前病历首页中的内容
     */
    public void saveCurrentPreCase(JSONObject caseInfo);


    /**
     * 提交最终版病历首页中的内容到数据库
     * @param caseInfo 当前病历首页中的内容
     */
    public void saveFinalPreCase(JSONObject caseInfo);


    /**
     * 将当前页面中的内容保存到病理模版中
     * @param caseInfo 当前模版中的内容
     */
    public void saveAsCaseTemplate(JSONObject caseInfo);


    /**
     * 查询该医生所有可用模版
     * @param doctorId 医生的id
     * @return 可用病历模版集合
     */
    public List<PatientCaseTemplate> listPatientTemplate(Integer doctorId);


    








}
