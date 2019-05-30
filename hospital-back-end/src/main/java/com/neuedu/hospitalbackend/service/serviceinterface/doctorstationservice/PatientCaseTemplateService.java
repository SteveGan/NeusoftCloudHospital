package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.poback.PatientCase;
import com.neuedu.hospitalbackend.model.poback.PatientCaseTemplate;

import java.util.List;

/**
 * 3.10病历模版管理
 * 提供了了模版管理服务方法，以及查询模版的方法
 * @author Steve
 */
public interface PatientCaseTemplateService {

    /**
     * 将当前页面中的内容保存到病理模版中
     * @param caseInfo 当前模版中的内容
     */
    void saveAsCaseTemplate(JSONObject caseInfo);


    /**
     * 查询该医生所有可用模版
     * @param doctorId 医生的id
     * @return 可用病历模版集合
     */
    List<PatientCaseTemplate> listCaseTemplate(Integer doctorId);


    /**
     * 查看该病人的历史过往病历
     * @param patientId 病人的id
     * @return 该病人的所有历史病历
     */
    List<PatientCase> listPastPatientCase(Integer patientId);


}
