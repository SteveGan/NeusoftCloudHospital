package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.Medicine;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 1.8 / 5.3 药品管理
 * @author Raven
 */
public interface MedicineManagementService {
    /**
     * 1.8.1 根据药品id查询药
     * @param id 疾病id
     */
    CommonResult getMedicineById(Short id);

    /**
     * 1.8.2 新增药品
     * @param medicine PO
     */
    CommonResult insertMedicine(Medicine medicine);

    /**
     * 1.8.3 修改药品
     * @param medicine PO
     */
    CommonResult updateMedicineById(Medicine medicine);

    /**
     * 1.8.4 删除药品
     * @param id 用户id
     */
    CommonResult deleteMedicineById(Short id);

    /**
     * 1.8.5 列出全部药品
     */
    CommonResult listAllMedicines();

    /**
     * 所有中药/西药
     */
    CommonResult listMedicineByType(Integer type);

}
