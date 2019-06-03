package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.TransactionExceptionLog;

public interface TransactionExceptionLogMapper {
    int insert(TransactionExceptionLog record);

    int insertSelective(TransactionExceptionLog record);
}