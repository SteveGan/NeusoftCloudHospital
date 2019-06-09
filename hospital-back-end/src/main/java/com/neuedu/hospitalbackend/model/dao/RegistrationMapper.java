package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.model.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RegistrationMapper {

    int insert(Registration registration);
    int insertSelective(Registration registration);
    Integer getNextId();
    int updateSelective(Registration registration);
    //列出指定用户的挂号信息
    Registration getRegistrationInfo(Integer registrationId);
    int updateStatusById(@Param("registrationId") Integer registrationId);
    //列出 所有未退号 信息列表
    List<Registration> listAllNormalRegistrationsInfo();
    //列出 所有退号 信息列表
    List<Registration> listAllRefundedRegistrationInfo();


}
