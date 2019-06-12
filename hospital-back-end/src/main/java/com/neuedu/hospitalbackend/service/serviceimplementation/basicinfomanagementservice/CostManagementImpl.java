package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.dao.CostMapper;
import com.neuedu.hospitalbackend.model.po.Cost;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.CostManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Raven
 * @Date: 2019/6/12 11:11 AM
 */
@Service
public class CostManagementImpl implements CostManagementService {
    @Resource
    private CostMapper costMapper;

    @Override
    public CommonResult listAllCosts() {
        List<Cost> list = costMapper.list();
        return CommonResult.success(list);
    }
}
