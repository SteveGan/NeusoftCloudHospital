package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.LoginLog;

public interface LoginLogMapper {
    int insert(LoginLog record);

    int insertSelective(LoginLog record);
}