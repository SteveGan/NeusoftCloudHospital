package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author Raven
 */
@Component
public interface MedicineMapper {
    Medicine get(Short id);

    int insert(Medicine medicine);

    int update(Medicine medicine);

    int delete(Short id);

    List<Medicine> list();

    BigDecimal getUnitPriceById(Integer id);

    HashMap getMedicineTypeAndUPrice(Integer id);

    HashMap getMedicineTypeAndUPriceByCode(String code);

    List<Medicine> listTraditionalMedicine();

    List<Medicine> listModernMedicine();

    String getCodeById(Integer id);

    Integer getIdByCode(String code);

    String getCostTypeById(Integer id);

    Integer getDepartmentIdByMedicineId(Integer id);
}
