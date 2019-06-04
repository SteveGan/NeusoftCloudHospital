package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface TransactionLogMapper {
    int insert(TransactionLog transactionLog);
    int insertSelective(TransactionLog transactionLog);
    int updateSelective(TransactionLog transactionLog);
    int selectStatusOfProject(@Param("collectionId") Integer collectionId, @Param("projectId") Integer projectId);

}
