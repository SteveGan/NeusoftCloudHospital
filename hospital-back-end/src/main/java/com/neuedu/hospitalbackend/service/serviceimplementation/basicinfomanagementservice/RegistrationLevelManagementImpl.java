package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.dao.RegistrationLevelMapper;
import com.neuedu.hospitalbackend.model.po.RegistrationLevel;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.RegistrationLevelManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.neuedu.hospitalbackend.util.ResultCode.E_602;

@Service
public class RegistrationLevelManagementImpl implements RegistrationLevelManagementService {
    @Resource
    RegistrationLevelMapper registrationLevelMapper;

    @Override
    public CommonResult getRegistrationLevelById(Short id) {
        RegistrationLevel registrationLevel = registrationLevelMapper.get(id);
        return CommonResult.success(registrationLevel);
    }

    @Override
    public CommonResult insertRegistrationLevel(RegistrationLevel registrationLevel) {
        int count = registrationLevelMapper.insert(registrationLevel);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult updateRegistrationLevelById(RegistrationLevel registrationLevel) {
        if (registrationLevelMapper.get(registrationLevel.getId()) == null) {
            return CommonResult.fail(E_602);
        }
        int count = registrationLevelMapper.update(registrationLevel);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult deleteRegistrationLevelById(Short id) {
        if (registrationLevelMapper.get(id) == null) {
            return CommonResult.fail(E_602);
        }
        int count = registrationLevelMapper.delete(id);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }
}
