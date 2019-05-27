package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.Department;

/**
 * 1.2 科室管理
 * @author Raven
 */
public interface DepartmentManagementService {

    /**
     * 1.2.1 根据部门id查询部分全部信息
     * @param id 部门id
     * @return
     */
    public Department selectDepartmentById(String id);
}
