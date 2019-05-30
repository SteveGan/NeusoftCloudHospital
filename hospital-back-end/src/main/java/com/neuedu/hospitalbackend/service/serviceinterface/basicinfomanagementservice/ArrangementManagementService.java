package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Arrangement;
import com.neuedu.hospitalbackend.model.po.ArrangementRule;

import java.util.List;

/**
 * 1.7 医生排班管理
 * @author Raven
 */
public interface ArrangementManagementService {

    /**
     * 1.7.1 设置排班规则
     * @param arrangementRule PO:排班规则
     */
    public void setArrangementRule(ArrangementRule arrangementRule);

    /**
     * 1.7.2 生成排班计划
     * @param object
     * @return
     */
    public List<Arrangement> generateArrangement(JSONObject object);
}
