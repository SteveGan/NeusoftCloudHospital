package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import org.springframework.stereotype.Component;

@Component
public interface TransactionLogMapper {
    int insert(TransactionLog transactionLog);
    int insertSelective(TransactionLog transactionLog);
}
