package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.alibaba.fastjson.JSONObject;

public interface PatientCostQueryService {


    /**
     * 查询该病历下项目的收费信息，返回的信息应该不仅包括：
     * 收费项目名称itemName和收费项目的金额itemCost
     * 前端再计算出一个总价格。
     * @param caseId 病历号id
     * @return 收费项目名称itemName和收费项目的金额itemCost
     */
     public JSONObject listPatientCost(int caseId);


}
