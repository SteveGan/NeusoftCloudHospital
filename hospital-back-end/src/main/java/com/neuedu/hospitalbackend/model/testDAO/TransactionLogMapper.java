package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.TransactionLog;

public interface TransactionLogMapper {
    int insert(TransactionLog record);

    int insertSelective(TransactionLog record);
}