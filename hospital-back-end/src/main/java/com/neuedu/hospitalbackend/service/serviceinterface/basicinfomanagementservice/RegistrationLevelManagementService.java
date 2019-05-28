package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.RegistrationLevel;

/**
 * 1.4 挂号级别管理
 * @author Raven
 */
public interface RegistrationLevelManagementService {

    /**
     * 1.2.1 根据挂号级别id查询挂号级别
     * @param id 挂号级别id
     * @return 查询结果
     */
    public RegistrationLevel getRegistrationLevel(String id);

    /**
     * 1.2.2 新增挂号级别
     * @param registrationLevel
     */
    public void insertRegistrationLevel(RegistrationLevel registrationLevel);

    /**
     * 1.2.3 修改挂号级别
     * @param registrationLevel
     */
    public void updateRegistrationLevelBy(RegistrationLevel registrationLevel);

    /**
     * 1.2.4 删除挂号级别
     * @param id
     */
    public void deleteDRegistrationLevelById(String id);

}
