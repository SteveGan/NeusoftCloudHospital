package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User userAuthentication(@Param("id") Integer userId, @Param("password") String password);

    User get(Integer id);

    int insert(User user);

    int update(User user);

    int delete(Integer id);

    List<User> listAllUsersAndRoles();


    /**
     * 设置用户的avatar
     * @param id
     * @param avatarUrl
     */
    void setAvatar(@Param("id") Integer id, @Param("avatar") String avatarUrl);


    /**
     * 设置用户的新密码
     * @param id 用户的Id
     * @param newPassword 用户的新密码
     */
    void setPassword(@Param("id") Integer id, @Param("newPassword") String newPassword);


}
