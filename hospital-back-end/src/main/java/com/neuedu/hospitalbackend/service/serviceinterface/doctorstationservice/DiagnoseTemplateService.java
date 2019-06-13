package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.neuedu.hospitalbackend.model.vo.DiagnoseTemplateParam;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 包含了诊断模版（常用诊断）的相应服务方法，增删改查
 */
public interface DiagnoseTemplateService {
    /**
     * 创建常用诊断
     * @param diagnoseTemplateParam
     */
    CommonResult saveDiagnoseTemplate(DiagnoseTemplateParam diagnoseTemplateParam);

    /**
     * 修改常用诊断
     * @param diagnoseTemplateParam
     */
    CommonResult modifyDiagnoseTemplate(DiagnoseTemplateParam diagnoseTemplateParam);

    /**
     * 查询常用诊断
     * @param roleId
     */
    CommonResult listMyDiagnoseTemplate(Integer roleId);

    /**
     * 删除
     * 删除常用诊断
     * @param roleId,diagnoseTemplateName
     * @return
     */
    CommonResult deleteMyDiagnoseTemplate(Integer roleId, String diagnoseTemplateName);


}
