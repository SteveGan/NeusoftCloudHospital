package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.DailySummaryLog;

public interface DailySummaryLogMapper {
    int insert(DailySummaryLog record);

    int insertSelective(DailySummaryLog record);
}