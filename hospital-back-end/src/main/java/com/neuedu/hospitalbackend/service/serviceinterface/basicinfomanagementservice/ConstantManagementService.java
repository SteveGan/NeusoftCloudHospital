package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.po.Constant;

import java.util.List;

/**
 * @author Raven
 */
public interface ConstantManagementService {

    /**
     * 返回所有
     * @return
     */
    List<Constant> list();
}
