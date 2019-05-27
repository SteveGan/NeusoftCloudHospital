package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.mapper.DepartmentMapper;
import com.neuedu.hospitalbackend.model.po.Department;

import javax.annotation.Resource;


/**
 * service方法实参类型: Field(String, Integer...), PO(相对JSONObject优先), JSONObject
 * service方法返回值类型: Field, PO, List, JSONObject
 */
public class DepartmentManagement {
    @Resource
    DepartmentMapper departmentMapper;

    /**
     * 根据部门id查询部分全部信息
     * @param id 部门id
     * @return
     */
    public Department selectDepartmentById(String id) {
        return departmentMapper.selectDepartmentById(id);
    }

    /**
     * 挂号
     *
     * @param obj
     * @return
     */
    public Department selectDepartmentByNameAndCode(JSONObject obj){
        // TODO: 解析PO / JSONObject

        // TODO: 从前端得到患者的身份证号

        // TODO: 从前端得到姓名(必填）、性别(必填)

        // TODO: 根据看诊医生和挂号级别，是否需要病历本，算出应收金额

        return null;
    }
}
