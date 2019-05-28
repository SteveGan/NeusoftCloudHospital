package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.alibaba.fastjson.JSONObject;

/**
 * 包含了医生工作量统计服务接口
 * @author Steve
 */
public interface DoctorStatisticService {

    /**
     * 查询特定时间段该医生诊治的所有病人，以及病人的项目收费统计
     * @param obj 应该包括起止时间和医生的id
     * @return 病人及其整料项目信息+费用
     */
    JSONObject listPatientsStatistics(JSONObject obj);

}
