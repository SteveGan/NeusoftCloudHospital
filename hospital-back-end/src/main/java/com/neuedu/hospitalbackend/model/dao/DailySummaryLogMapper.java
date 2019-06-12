package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.DailySummaryLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface DailySummaryLogMapper {

    int insert(DailySummaryLog dailySummaryLog);

    int insertSelective(DailySummaryLog dailySummaryLog);

    //查询指定收费员上次日结截止时间
    String getEndDateByCashierId(Integer cashierId);

    //查询日结历史记录
    List<HashMap> getByCashierIdAndDate(@Param("cashierId") Integer cashierId, @Param("beginDateStr") String beginDateStr,
                                        @Param("endDateStr") String endDateStr);

    HashMap getInvoiceRangeByCashierIdAndDate(@Param("cashierId") Integer cashierId, @Param("createDate") String createDate);

    //查询收银员第一次日结时间
    String getFirstSummaryDate(Integer cashierId);

}
