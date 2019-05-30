package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.TransactionLog;

public interface TransactionLogMapper {
    int insert(TransactionLog record);

    int insertSelective(TransactionLog record);
}