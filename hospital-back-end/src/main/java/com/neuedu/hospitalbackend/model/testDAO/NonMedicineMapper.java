package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.NonMedicine;

public interface NonMedicineMapper {
    int insert(NonMedicine record);

    int insertSelective(NonMedicine record);
}