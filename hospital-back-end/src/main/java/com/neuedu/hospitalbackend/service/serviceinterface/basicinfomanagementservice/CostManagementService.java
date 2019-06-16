package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.Cost;
import com.neuedu.hospitalbackend.util.CommonResult;

public interface CostManagementService {
    /**
     * 1.2.5 列出全部科目
     */
    CommonResult listAllCosts();

    CommonResult updateCostById(Cost cost);

    CommonResult deleteCostById(Integer id);

    /**
     * 根据id获取科目信息
     */
    CommonResult getCostById(Integer id);

    /**
     * 新增科目信息
     */
    CommonResult insertCost(Cost cost);

}
