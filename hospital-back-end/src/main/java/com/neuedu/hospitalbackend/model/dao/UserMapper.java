package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User userAuthentication(@Param("id") Integer userId, @Param("password") String password);
}