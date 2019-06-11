package com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice;

import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.model.vo.DailySummaryParam;
import com.neuedu.hospitalbackend.util.CommonResult;

import java.util.List;

public interface DailySummaryService {


    /**
     * 日结统计：根据收费员id、起始日期查询指定收费员的所有发票信息
     * @param dailySummaryParam
     * @return
     */
    CommonResult listLogsByCashierIdAndDate(DailySummaryParam dailySummaryParam);

    /**
     * 结算报账：对统计时间段的收费记录，进行冻结状态
     * @param dailySummaryParam
     * @return
     */
    CommonResult freezeTransactionLogs(DailySummaryParam dailySummaryParam);

}
