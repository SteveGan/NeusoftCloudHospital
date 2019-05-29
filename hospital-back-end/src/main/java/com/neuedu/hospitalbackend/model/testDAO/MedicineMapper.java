package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Medicine;

public interface MedicineMapper {
    int insert(Medicine record);

    int insertSelective(Medicine record);
}