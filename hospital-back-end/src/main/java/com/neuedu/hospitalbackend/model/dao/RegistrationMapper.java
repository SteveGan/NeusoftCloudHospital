package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.model.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public interface RegistrationMapper {

    int insert(Registration registration);
    int insertSelective(Registration registration);
    Integer getNextId();
    int updateSelective(Registration registration);
    Registration getRegistrationInfo(Integer registrationId);
    int updateStatusById(@Param("registrationId") Integer registrationId);
}
