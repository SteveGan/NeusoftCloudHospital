package com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice;

import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.util.CommonResult;


public interface DailySummaryService {


    /**
     * 根据收费员id查询上次日结的截止时间
     */
    CommonResult getLastEndDate(Integer cashierId);

    /**
     * 日结统计：根据收费员id、起始日期查询指定收费员的所有发票信息
     * @param dailySummaryParam 日结参数
     * @return
     */
    CommonResult listLogsByCashierIdAndDate(DailySummaryParam dailySummaryParam);

    /**
     * 结算报账：对统计时间段的收费记录，进行冻结状态
     * @param dailySummaryParam 日结参数
     * @return
     */
    CommonResult freezeTransactionLogs(DailySummaryParam dailySummaryParam);

    /**
     * 日结历史查询
     * @param dailySummaryParam 日结参数
     * @return
     */
    CommonResult listHistorySummaryLogs(DailySummaryParam dailySummaryParam);

    /**
     * 查询指定收费员第一条日结记录的时间
     * @param cashierId
     * @return
     */
    CommonResult getFirstSummaryDate(Integer cashierId);
}
