package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;

import com.neuedu.hospitalbackend.model.po.Diagnose;
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
     * 修改常用诊断
     * @param roleId
     */
    CommonResult listMyDiagnoseTemplate(Integer roleId);





//
//    /**
//     * 查询该医生的常用诊断
//     * @param doctorId 医生的id
//     * @return 诊断模版的列表
//     */
//   List<DiagnoseTemplate> listDiagnoseTemplate(int doctorId);
//
//
//    /**
//     * 删除相应的诊断模版
//     * @param diagnoseTemplateId id of the diagnose template
//     */
//    void removeDiagnoseTemplate(int diagnoseTemplateId);
//
//
//    /**
//     * 更新当前诊断模版内容
//     * @param obj 诊断模版的内容
//     */
//    void updateDiagnoseTemplate(JSONObject obj);


}
