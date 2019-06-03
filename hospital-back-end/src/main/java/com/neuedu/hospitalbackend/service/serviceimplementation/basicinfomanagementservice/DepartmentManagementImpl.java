package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.dao.DepartmentMapper;
import com.neuedu.hospitalbackend.model.po.Department;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.DepartmentManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.neuedu.hospitalbackend.util.ResultCode.E_601;
import static com.neuedu.hospitalbackend.util.ResultCode.E_604;

/**
 * @author Raven
 */
@Service
public class DepartmentManagement implements DepartmentManagementService {
    @Resource
    DepartmentMapper departmentMapper;

    /**
     * 根据部门id查询部分全部信息
     * @param id 部门id
     * @return
     */
    @Override
    public CommonResult getDepartmentById(Integer id) {
        Department department = departmentMapper.get(id);
        return CommonResult.success(department);
    }

    @Override
    public CommonResult insertDepartment(Department department) {
        int count = departmentMapper.insert(department);
        if (count == 0) {
            return CommonResult.fail(E_601);
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult updateDepartmentById(Department department) {
        if (departmentMapper.get(department.getId()) == null) {
            return CommonResult.fail(E_604);
        }
        int count = departmentMapper.update(department);
        return CommonResult.success(count);
    }

    @Override
    public CommonResult deleteDepartmentById(Integer id) {
        if (departmentMapper.get(id) == null) {
            return CommonResult.fail(E_604);
        }
        int count = departmentMapper.delete(id);
        if (count == 0) {
            return CommonResult.fail(E_601);
        }
        return CommonResult.success(count);
    }
}
