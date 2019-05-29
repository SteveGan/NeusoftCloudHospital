package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.DailySummaryLog;

public interface DailySummaryLogMapper {
    int insert(DailySummaryLog record);

    int insertSelective(DailySummaryLog record);
}