package com.neuedu.hospitalbackend.model.testdao;

import com.neuedu.hospitalbackend.model.po.Registration;

public interface RegistrationMapper {
    int insert(Registration record);

    int insertSelective(Registration record);
}