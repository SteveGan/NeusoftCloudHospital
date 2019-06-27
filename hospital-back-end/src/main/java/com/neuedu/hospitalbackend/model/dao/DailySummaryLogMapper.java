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

    //当每日挂号收费员报账时，通过该功能查询收费员应该报账金额，报账发票数量
    DailySummaryLog getSummaryLog(@Param("cashierId") Integer cashierId, @Param("beginDateStr") String beginDateStr,
                                  @Param("endDateStr") String endDateStr);

    //核对通过
    int updateResult(@Param("cashierId") Integer cashierId, @Param("assessorId") Integer assessorId,
                     @Param("beginDateStr") String beginDateStr, @Param("endDateStr") String endDateStr);
}
