package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}