package com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice;

import com.alibaba.fastjson.JSONObject;

public interface PaymentService {

    /**
     * 通过患者病历号，查询收费项目
     * @param object 病历号，status
     * @return 收费项目
     */
    JSONObject listUnpaidItems(JSONObject object);

    /**
     *  查找想要退费的项目状态
     * @param object 退费项目的collection_id, project_id
     * @return 退费的项目
     */
    Object getItem(Object object);

    /**
     * 在检查/检验/处置/药品表中更改想要退费的项目状态
     * @param object 退费项目的collection_id, project_id
     */
    void updateItemStatus(Object object);



}
