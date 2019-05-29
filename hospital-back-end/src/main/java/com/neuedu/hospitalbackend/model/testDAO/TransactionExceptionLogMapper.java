package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.TransactionExceptionLog;

public interface TransactionExceptionLogMapper {
    int insert(TransactionExceptionLog record);

    int insertSelective(TransactionExceptionLog record);
}