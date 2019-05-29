package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}