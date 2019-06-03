package com.neuedu.hospitalbackend.model.dao;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface RegistrationLevelMapper {
    BigDecimal getRegistrationLevelCostById(Short id);
}
