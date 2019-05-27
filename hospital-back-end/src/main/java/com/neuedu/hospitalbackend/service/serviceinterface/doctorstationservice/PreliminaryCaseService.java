package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.PatientCaseTemplate;

import java.util.List;


/**
 * 3.1  门诊病历首页
 * 包含关于门诊病历首页的service方法
 * @author Steve
 */
public interface PreliminaryCaseService {


    /**
     * get all  waited patient of the doctor
     * @param doctorId of the doctor
     * @return list of patient
     */
    public List<Patient> listWaitedPatients(Integer doctorId);

    /**
     * 将当前病历首页中的内容暂时存入数据库
     * @param caseInfo 当前病历首页中的内容
     */
    public void savePreCase(JSONObject caseInfo);

    /**
     * 提交最终版病历首页中的内容保存到数据库, 注意：最终版本
     * @param caseInfo 当前病历首页中的内容
     */
    public void submitPreCase(JSONObject caseInfo);


}
