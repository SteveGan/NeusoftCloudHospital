package com.neuedu.hospitalbackend.model.dao;

import com.neuedu.hospitalbackend.model.po.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> listRole(Integer userId);
    Integer getDepartmentIdByRoleId(Integer roleId);
}
