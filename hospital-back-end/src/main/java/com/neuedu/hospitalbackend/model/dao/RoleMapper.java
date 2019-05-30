package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Role;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);
}