package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Role;

import java.util.List;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    List<Role> listRole(Integer userId);
}