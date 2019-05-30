package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Treatment;
import com.neuedu.hospitalbackend.model.bo.TreatmentProject;
import java.util.List;

public interface TreatmentManagementService {


    /**
     * 返回所有的处置项目
     * @return 所有的处置项目
     */
    List<TreatmentProject> listAllTreatmentProject();

    /**
     * 返回所有的处置项目信息
     * @return 所有处置项目
     */
    List<Treatment> listAllTreatment();

    /**
     * 添加新的处置项目组
     * @param obj 处置项目组信息
     */
    void insertTreatment(JSONObject obj);


    /**
     * 更新treatment内容
     * @param obj 处置项目组内容
     */
    void updateTreatment(JSONObject obj);

}
