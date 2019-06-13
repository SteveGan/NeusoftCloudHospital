package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.bo.Project;
import com.neuedu.hospitalbackend.model.vo.CollectionParam;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.util.CommonResult;

import java.util.List;

/**
 * 包含了医生开 检查/检验 需要用到的service方法
 *
 * @author Steve
 */
public interface ProjectCollectionManagementService {

    /**
     * 根据当前病历号，找到目前所有的检验申请单
     * @param caseId
     */
    CommonResult listCollections(Integer caseId, Integer type);

    /**
     * 申请新的申请清单
     * @param collectionType
     */
    CommonResult applyNewCollection(Integer collectionType);

    /**
     * 暂存申请清单
     * @param collectionParam
     */
    CommonResult savePresentCollection(CollectionParam collectionParam);

    /**
     * 开立申请清单
     * @param collectionParam
     */
    CommonResult submitPresentCollection(CollectionParam collectionParam);

    /**
     * 作废开立项目
     * @param collectionId
     */
    CommonResult cancelSubmittedCollection(Integer collectionId, Integer type);


    /**
     * 删除检查检验处置清单或处方
     */
    CommonResult deleteCollection(Integer collectionId, Integer type);

    /**
     * 获取项目检查结果
     * @param projectParam
     */
    CommonResult getProjectResult(ProjectParam projectParam);


}
