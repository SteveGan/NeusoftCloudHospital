package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.Department;

/**
 * 1.2 科室管理
 * @author Raven
 */
public interface DepartmentManagementService {

    /**
     * 1.2.1 根据科室id查询科室
     * @param id 科室id
     * @return 查询结果
     */
    public Department getDepartmentById(String id);

    /**
     * 1.2.2 新增科室
     * @param department
     */
    public void insertDepartment(Department department);

    /**
     * 1.2.3 修改科室
     * @param department
     */
    public void updateDepartmentBy(Department department);

    /**
     * 1.2.4 删除科室
     * @param id
     */
    public void deleteDepartmentById(String id);

}
