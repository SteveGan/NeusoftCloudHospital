package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Invoice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface InvoiceMapper {
    int insert(String id);
    String getAvailableInvoiceCode();
    int updateInvoiceStatusById(@Param("status") Byte status, @Param("id") String id);
    List<HashMap> getInvoiceInfo();
    List<Invoice> list();
}
