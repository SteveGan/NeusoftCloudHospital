package com.neuedu.hospitalbackend.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int getUserById(@Param("id") Integer userId, @Param("password") String password);
}
