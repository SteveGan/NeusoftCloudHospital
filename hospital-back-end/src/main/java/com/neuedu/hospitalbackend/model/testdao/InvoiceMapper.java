package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Invoice;

public interface InvoiceMapper {
    int insert(Invoice record);

    int insertSelective(Invoice record);
}