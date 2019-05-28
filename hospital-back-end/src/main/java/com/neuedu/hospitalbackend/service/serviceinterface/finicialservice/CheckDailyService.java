package com.neuedu.hospitalbackend.service.serviceinterface.finicialservice;

import com.alibaba.fastjson.JSONObject;

/**
 * 6.2 门诊日结核对
 * @author Raven
 */
public interface CheckDailyService {

    /**
     * 6.2.1 门诊日结核对
     * 应用场景：当每日挂号收费员报账时，通过该功能查询收费员应该报账金额，报账发票数量。
     * 操作描述：指定挂号收费员，点击查询，系统自动查出该收费员应交而尚未交账的金额、发票信息，
     * 当上述查询出的结果和挂号员实际上交的材料一致时，点击“核对通过”按钮，即完成财务入库操作。
     * @param object
     * @return
     */
    JSONObject checkDaily(JSONObject object);
}
