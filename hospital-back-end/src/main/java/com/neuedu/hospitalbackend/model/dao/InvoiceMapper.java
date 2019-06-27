package com.neuedu.hospitalbackend.model.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface InvoiceMapper {
    int insert(String id);
    String getAvailableInvoiceCode();
    int updateInvoiceStatusById(@Param("status") Byte status, @Param("id") String id);
}
