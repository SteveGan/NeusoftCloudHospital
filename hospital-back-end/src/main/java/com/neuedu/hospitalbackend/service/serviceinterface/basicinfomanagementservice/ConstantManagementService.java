package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.Constant;
import com.neuedu.hospitalbackend.util.CommonResult;

import java.util.List;

/**
 * 1.1
 * @author Raven
 */
public interface ConstantManagementService {

    /**
     * 返回所有
     * @return
     */
    List<Constant> list();

    /**
     * 1.1.1 树状图列出常量表
     */
    CommonResult listConstantsTree();
}
