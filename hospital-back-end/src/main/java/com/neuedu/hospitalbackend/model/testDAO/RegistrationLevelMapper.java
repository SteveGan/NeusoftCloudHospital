package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.RegistrationLevel;

public interface RegistrationLevelMapper {
    int insert(RegistrationLevel record);

    int insertSelective(RegistrationLevel record);
}