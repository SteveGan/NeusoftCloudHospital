package com.neuedu.hospitalbackend.service.serviceinterface.finicialservice;

import com.neuedu.hospitalbackend.model.poback.Cost;

/**
 * 6.1 费用科目管理(基础信息查询)
 * @author Raven
 */
public interface CostManagementService {

    /**
     * 6.1.1 查询费用科目
     * @return 费用科目
     */
    Cost getCostById(String id);

    /**
     * 6.1.2 新增费用科目
     * @param cost
     */
    void insertCost(Cost cost);

    /**
     * 6.1.3 修改费用科目
     * @param cost
     */
    void updateCostById(Cost cost);

    /**
     * 6.1.4 删除费用科目
     * @param id
     */
    void deleteCostById(String id);
}
