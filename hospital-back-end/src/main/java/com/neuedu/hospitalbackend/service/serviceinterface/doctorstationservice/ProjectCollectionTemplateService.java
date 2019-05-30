package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.ProjectCollectionTemplate;
import java.util.List;

/**
 * 包含检查/检验 组套管理方法
 */
public interface ProjectCollectionTemplateService {


    /**
     * 返回该医生可用的检查/检验 模版
     * @param doctorId 医生的id
     * @return 返回所有可用的模版
     */
    List<ProjectCollectionTemplate> listProjectCollectionTemplate(int doctorId);


    /**
     * 添加新的检查/检验模版
     * @param obj 检查检验模版内容
     */
    void insertProjectCollectionTemplate(JSONObject obj);


    /**
     * 删除相应的检查/检验模版
     * @param templateId 模版的id
     */
    void deleteProjectCollectionTemplate(int templateId);


    /**
     * 更新检查/检验模版
     * @param obj 检查检验模版内容
     */
    void updateProjectCollectionTemplate(JSONObject obj);
}
