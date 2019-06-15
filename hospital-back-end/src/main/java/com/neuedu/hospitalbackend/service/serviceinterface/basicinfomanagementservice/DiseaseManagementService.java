package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.Disease;
import com.neuedu.hospitalbackend.model.po.User;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 1.5 诊断目录管理
 * @author Raven
 */
public interface DiseaseManagementService {

    /**
     * 1.5.1 根据疾病id查询疾病
     * @param id 疾病id
     */
    CommonResult getDiseaseById(Short id);

    /**
     * 1.5.2 新增疾病
     * @param disease PO
     */
    CommonResult insertDisease(Disease disease);

    /**
     * 1.5.3 修改疾病
     * @param disease PO
     */
    CommonResult updateDiseaseById(Disease disease);

    /**
     * 1.5.4 删除疾病
     * @param id 用户id
     */
    CommonResult deleteDiseaseById(Short id);

    /**
     * 1.5.5 列出全部疾病
     */
    CommonResult listAllDiseases();

    /**
     * 获取西医或者疾病
     */
    CommonResult listDiseaseByType(Integer type);
}
