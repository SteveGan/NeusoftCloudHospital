package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Registration;

public interface RegistrationMapper {
    int insert(Registration record);

    int insertSelective(Registration record);
}