package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.neuedu.hospitalbackend.model.po.Medicine;
import com.neuedu.hospitalbackend.model.po.Recipe;
import com.sun.tools.javac.util.List;

/**
 * 提供医生端的药方管理服务方法
 * @author Steve
 */
public interface RecipeManagementService {


    /**
     * 查询所有药品信息
     * @return 所有药品
     */
    public List<Medicine> listAllMedicine();



}
