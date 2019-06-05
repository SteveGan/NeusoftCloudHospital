package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Medicine;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Raven
 */
@Mapper
public interface MedicineMapper {
    Medicine get(Short id);

    int insert(Medicine medicine);

    int update(Medicine medicine);

    int delete(Short id);
}
