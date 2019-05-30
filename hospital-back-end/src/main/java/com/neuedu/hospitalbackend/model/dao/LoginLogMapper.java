package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.LoginLog;

public interface LoginLogMapper {
    int insert(LoginLog record);

    int insertSelective(LoginLog record);
}