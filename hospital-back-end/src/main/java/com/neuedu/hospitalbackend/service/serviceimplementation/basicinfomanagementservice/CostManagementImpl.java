package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.dao.CostMapper;
import com.neuedu.hospitalbackend.model.po.Cost;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.CostManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_602;

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

    @Override
    public CommonResult updateCostById(Cost cost){
        if (costMapper.get(cost.getId()) == null) {
            return CommonResult.fail(E_602);
        }
        int count = costMapper.update(cost);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult deleteCostById(Integer id){
        if (costMapper.get(id) == null) {
            return CommonResult.fail(E_602);
        }
        int count = costMapper.delete(id);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

}
