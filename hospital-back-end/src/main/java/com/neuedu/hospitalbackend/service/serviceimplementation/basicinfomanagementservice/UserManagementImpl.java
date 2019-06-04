package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.UserManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

@Service
public class UserManagementImpl implements UserManagementService {
    @Override
    public CommonResult getUserById(Integer id) {
        return null;
    }

    @Override
    public CommonResult insertUser(User user) {
        return null;
    }

    @Override
    public CommonResult updateUserById(User user) {
        return null;
    }

    @Override
    public CommonResult deleteUserById(Integer id) {
        return null;
    }
}
