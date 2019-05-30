package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User userAuthentication(@Param("id") Integer userId, @Param("password") String password);
    List<User> listAllUsersAndRoles();
}
