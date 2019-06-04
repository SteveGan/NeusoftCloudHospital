package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.RegistrationLevel;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 1.4 挂号级别管理
 * 应用场景：用于医院管理员维护医院所使用的挂号级别，以及每种挂号级别的相应挂号费。如普通号、专家号、急诊号等。
 * 挂号级别信息包括：号别编码、号别名称、挂号限额、显示顺序号、挂号费。
 * @author <a href="https://ravenxu.top/">Raven</a>
 */
public interface RegistrationLevelManagementService {

    /**
     * 1.4.1 根据挂号级别id查询挂号级别
     * @param id 挂号级别id
     * @return 查询结果
     */
    CommonResult getRegistrationLevelById(Short id);

    /**
     * 1.4.2 新增挂号级别
     * @param registrationLevel PO
     */
    CommonResult insertRegistrationLevel(RegistrationLevel registrationLevel);

    /**
     * 1.4.3 修改挂号级别
     * @param registrationLevel PO
     */
    CommonResult updateRegistrationLevelById(RegistrationLevel registrationLevel);

    /**
     * 1.4.4 删除挂号级别
     * @param id
     */
    CommonResult deleteRegistrationLevelById(Short id);

}
