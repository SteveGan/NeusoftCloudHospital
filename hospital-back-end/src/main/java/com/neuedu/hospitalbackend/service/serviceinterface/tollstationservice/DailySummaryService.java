package com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.poback.DailySummaryLog;
import com.neuedu.hospitalbackend.model.poback.TransactionLog;

import java.util.List;

public interface DailySummaryService {

    /*---- 2.8 收费员日结 ----*/
    /**
     * 2.8.1 日结
     * 查询上次日结日期
     * @param casherId
     * @return 最近一次日结日期 （注意：String）
     * TODO: 从daily_summary取出该收费员最近一次日结日期
     */
    String getLatestDailySummaryDate(Integer casherId);

    /**
     * 计算日结时间内总收费金额
     * @param jsonObject:{beginDate: 上次日结日期
     *                      endDate: 当前日结日期
     *                      casherId：收费员id}
     * @return sum(total_money)
     */
    Double getSumMoney(JSONObject jsonObject);

    /**
     * 计算日结时间内开始发票号和最后发票号
     * @param jsonObject:{beginDate: 上次日结日期
     *                      endDate: 当前日结日期
     *                      casherId：收费员id}
     * @return jsonObject:{beginInvoiceCode, endInvoiceCode}
     */
    JSONObject listBeginAndEndInvoiceCode(JSONObject jsonObject);

    /**
     * 插入日结信息
     * 从以上方法得到的数据统合为一个日结记录对象，插入到数据库汇总
     * @param dailySummaryLog
     */
    void insertDailySummary(DailySummaryLog dailySummaryLog);


    /*2.8.1 日结历史查询*/

    /**
     * 获取时间范围内该收费员所有日结信息
     * @param jsonObject:{beginDate, endDate, casherId}
     * @return
     */
    List<DailySummaryLog> listDailySummaryByCasherId(JSONObject jsonObject);

    /**
     * 点击查询某一日结记录具体信息
     * TODO: 日结对应所有缴费记录信息
     * @param dailySummaryId
     * @return 缴费清单列表
     */
    List<TransactionLog> listDailySummaryLog(Integer dailySummaryId);



}
