package com.neuedu.hospitalbackend.service.serviceinterface.finicialservice;

import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.util.CommonResult;

public interface CheckDailyService {

    /**
     * 门诊日结信息
     * @param dailySummaryParam 指定挂号收费员、查询日期
     * @return
     */
    CommonResult checkDailySummary(DailySummaryParam dailySummaryParam);
}
