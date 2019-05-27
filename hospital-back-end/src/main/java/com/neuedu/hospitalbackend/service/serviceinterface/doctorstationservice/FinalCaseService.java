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


    public void saveFinalCase(JSONObject obj);

}
