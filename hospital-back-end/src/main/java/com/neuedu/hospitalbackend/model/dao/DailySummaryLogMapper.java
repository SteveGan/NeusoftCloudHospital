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

    List<DailySummaryLog> getByCashierIdAndDate(@Param("cashierId") Integer cashierId, @Param("beginDateStr") String beginDateStr,
                                        @Param("endDateStr") String endDateStr);
}
