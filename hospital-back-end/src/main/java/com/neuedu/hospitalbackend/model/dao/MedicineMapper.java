package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Medicine;

public interface MedicineMapper {
    int insert(Medicine record);

    int insertSelective(Medicine record);
}