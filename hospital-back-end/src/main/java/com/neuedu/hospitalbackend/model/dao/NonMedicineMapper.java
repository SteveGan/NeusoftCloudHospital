package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.NonMedicine;

public interface NonMedicineMapper {
    int insert(NonMedicine record);

    int insertSelective(NonMedicine record);
}