package com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice;

import com.neuedu.hospitalbackend.util.CommonResult;

public interface TechProjectManagementService {

    /**
     * 请求检查/检验项目id,name和部门name
     * @param type 1.检查 2.检验 3.处置
     * @return
     */
    CommonResult listProjectBasicInfoByType(Integer type);

    /**
     * 请求项目下小项id,name,unit
     * @param projectId
     * @return
     */
    CommonResult listItemBasicInfoByProject(Integer projectId);
    
}
