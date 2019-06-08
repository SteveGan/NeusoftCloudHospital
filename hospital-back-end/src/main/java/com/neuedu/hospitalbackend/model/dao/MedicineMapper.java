package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Raven
 */
@Component
public interface MedicineMapper {
    Medicine get(Short id);

    int insert(Medicine medicine);

    int update(Medicine medicine);

    int delete(Short id);

    BigDecimal getUnitPrizeById(Integer id);
}
