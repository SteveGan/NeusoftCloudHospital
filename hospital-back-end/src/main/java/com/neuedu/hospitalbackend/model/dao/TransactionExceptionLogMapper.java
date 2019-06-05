package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.TransactionExceptionLog;
import org.springframework.stereotype.Component;

@Component
public interface TransactionExceptionLogMapper {

    int insert(TransactionExceptionLog transactionExceptionLog);
    int insertSelective(TransactionExceptionLog transactionExceptionLog);
}
