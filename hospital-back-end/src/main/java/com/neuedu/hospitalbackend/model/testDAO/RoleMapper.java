package com.neuedu.hospitalbackend.model.testDAO;

import com.neuedu.hospitalbackend.model.testPO.Role;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);
}