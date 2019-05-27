package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.DiagnoseTemplate;
import com.sun.tools.javac.util.List;


/**
 * 包含了诊断模版（常用诊断）的相应服务方法，增删改查
 * @author Steve
 */
public interface DiagnoseTemplateService {

    /**
     * 查询该医生的常用诊断
     * @param doctorId 医生的id
     * @return 诊断模版的列表
     */
   List<DiagnoseTemplate> listDiagnoseTemplate(int doctorId);


    /**
     * 删除相应的诊断模版
     * @param diagnoseTemplateId id of the diagnose template
     */
    void removeDiagnoseTemplate(int diagnoseTemplateId);


    /**
     * 将诊断模版内容存入数据库
     * @param obj 诊断模版的内容
     */
    void insertDiagnoseTemplate(JSONObject obj);


    /**
     * 更新当前诊断模版内容
     * @param obj 诊断模版的内容
     */
    void updateDiagnoseTemplate(JSONObject obj);


}
