package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.NonMedicine;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NonMedicineMapper {
    NonMedicine get(Integer id);

    int insert(NonMedicine nonMedicine);

    int update(NonMedicine nonMedicine);

    int delete(Integer id);
}
