package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.neuedu.hospitalbackend.model.vo.CollectionTemplateParam;
import com.neuedu.hospitalbackend.util.CommonResult;


/**
 * 包含检查/检验 组套管理方法
 */
public interface ProjectCollectionTemplateService {

    /**
     * 申请检查检验处置模板
     */
    CommonResult insertCollectionTemplate(CollectionTemplateParam collectionTemplateParam);



}
