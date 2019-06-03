package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.TransactionLog;

public interface TransactionLogMapper {
    int insert(TransactionLog record);

    int insertSelective(TransactionLog record);
}