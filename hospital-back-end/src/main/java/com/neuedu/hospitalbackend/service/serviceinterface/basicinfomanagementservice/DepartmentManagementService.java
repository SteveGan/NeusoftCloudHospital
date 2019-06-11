package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 1.2 科室管理
 * @author Raven
 */
public interface DepartmentManagementService {

    /**
     * 1.2.1 根据科室id查询科室
     * @param id 科室id
     */
    CommonResult getDepartmentById(Integer id);

    /**
     * 1.2.2 新增科室
     * @param department PO
     */
    CommonResult insertDepartment(Department department);

    /**
     * 1.2.3 修改科室
     * @param department PO
     */
    CommonResult updateDepartmentById(Department department);

    /**
     * 1.2.4 删除科室
     * @param id 科室id
     */
    CommonResult deleteDepartmentById(Integer id);

    /**
     * 1.2.5 列出全部科室
     */
    CommonResult listAllDepartments();

    /**
     * 1.2.6 分页列出科室
     */
    CommonResult listPage(Integer pageNum, Integer pageSize);

    /**
     * 1.2.7 树状图列出科室
     */
    CommonResult listDepartmentsTree();


    /**
     * 根据类型列出科室
     */
    CommonResult listDepartmentsByType(Integer type);
}
