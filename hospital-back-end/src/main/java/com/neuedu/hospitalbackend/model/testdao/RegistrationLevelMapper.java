package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.RegistrationLevel;

public interface RegistrationLevelMapper {
    int insert(RegistrationLevel record);

    int insertSelective(RegistrationLevel record);
}