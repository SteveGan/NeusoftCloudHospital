package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.RegistrationLevel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Mapper
public interface RegistrationLevelMapper {
    BigDecimal getRegistrationLevelCostById(Short id);

    RegistrationLevel get(Short id);

    int insert(RegistrationLevel registrationLevel);

    int update(RegistrationLevel registrationLevel);

    int delete(Short id);
}
