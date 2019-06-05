package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Medicine;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MedicineMapper {
    Medicine get(Short id);

    int insert(Medicine nonMedicine);

    int update(Medicine nonMedicine);

    int delete(Short id);
}
