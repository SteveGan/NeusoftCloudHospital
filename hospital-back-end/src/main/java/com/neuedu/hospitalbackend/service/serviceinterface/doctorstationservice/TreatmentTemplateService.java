package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.TreatmentTemplate;
import com.sun.tools.javac.util.List;

public interface TreatmentTemplateService {


    /**
     * 返回该医生可用的处置模版
     * @param doctorId 医生的id
     * @return 所欲可用的模版
     */
    List<TreatmentTemplate> listTreatmentTemplate(int doctorId);


    /**
     * 加入新的处置模版
     * @param obj 处置模版内容
     */
    void insertTreatmentTemplate(JSONObject obj);


    /**
     * 更新处置模版的内容
     * @param obj 处置模版的内容
     */
    void updateTreatmentTemplate(JSONObject obj);


    /**
     * 删除处置模版
     * @param templateId 被删除模版的id
     */
    void deleteTreatmentTemplate(int templateId);
}
