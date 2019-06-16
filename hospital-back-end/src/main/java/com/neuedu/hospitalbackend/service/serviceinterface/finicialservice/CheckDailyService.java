package com.neuedu.hospitalbackend.service.serviceinterface.finicialservice;

import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 门诊日结核对
 * 当每日挂号收费员报账时，通过该功能查询收费员应该报账金额，报账发票数量。
 * @author Polaris
 */
public interface CheckDailyService {

    /**
     * 门诊日结信息
     * @param dailySummaryParam 指定挂号收费员、查询日期
     * @return 执行结果
     */
    CommonResult checkDailySummary(DailySummaryParam dailySummaryParam);
}
