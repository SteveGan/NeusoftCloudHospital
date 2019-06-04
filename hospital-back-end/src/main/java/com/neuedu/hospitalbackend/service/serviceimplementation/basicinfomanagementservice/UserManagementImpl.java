package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.dao.UserMapper;
import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.UserManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.neuedu.hospitalbackend.util.ResultCode.E_602;

@Service
public class UserManagementImpl implements UserManagementService {
    @Resource
    UserMapper userMapper;

    @Override
    public CommonResult getUserById(Integer id) {
        User user = userMapper.get(id);
        return CommonResult.success(user);
    }

    @Override
    public CommonResult insertUser(User user) {
        int count = userMapper.insert(user);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult updateUserById(User user) {
        if (userMapper.get(user.getId()) == null) {
            return CommonResult.fail(E_602);
        }
        int count = userMapper.update(user);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult deleteUserById(Integer id) {
        if (userMapper.get(id) == null) {
            return CommonResult.fail(E_602);
        }
        int count = userMapper.delete(id);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }
}
