package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.NonMedicine;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 1.6 / 4.4 非药品收费项目管理
 * @author Raven
 */
public interface NonMedicineManagementService {

    /**
     * 1.6.1 根据非药品项目id查询非药品项目
     * @param id 疾病id
     */
    CommonResult getNonMedicineById(Integer id);

    /**
     * 1.6.2 新增非药品项目
     * @param disease PO
     */
    CommonResult insertNonMedicine(NonMedicine disease);

    /**
     * 1.6.3 修改非药品项目
     * @param disease PO
     */
    CommonResult updateNonMedicineById(NonMedicine disease);

    /**
     * 1.6.4 删除非药品项目
     * @param id 用户id
     */
    CommonResult deleteNonMedicineById(Integer id);
}
