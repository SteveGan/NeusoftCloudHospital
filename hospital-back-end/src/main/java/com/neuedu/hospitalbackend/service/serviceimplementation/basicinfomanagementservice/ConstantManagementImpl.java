package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.dao.ConstantMapper;
import com.neuedu.hospitalbackend.model.po.Constant;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.ConstantManagementService;

import javax.annotation.Resource;
import java.util.List;

public class ConstantManagementImpl implements ConstantManagementService {
    @Resource
    ConstantMapper constantMapper;

    @Override
    public List<Constant> list() {
        List<Constant> list = constantMapper.list();
        return null;
    }
}
