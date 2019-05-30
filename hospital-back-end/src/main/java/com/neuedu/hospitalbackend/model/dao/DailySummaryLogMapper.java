package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.DailySummaryLog;

public interface DailySummaryLogMapper {
    int insert(DailySummaryLog record);

    int insertSelective(DailySummaryLog record);
}