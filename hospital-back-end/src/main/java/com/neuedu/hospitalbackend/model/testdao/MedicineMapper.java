package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Medicine;

public interface MedicineMapper {
    int insert(Medicine record);

    int insertSelective(Medicine record);
}