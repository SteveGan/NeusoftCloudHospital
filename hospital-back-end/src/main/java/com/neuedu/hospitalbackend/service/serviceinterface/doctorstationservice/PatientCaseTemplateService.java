package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.neuedu.hospitalbackend.model.po.DiagnoseTemplate;
import com.neuedu.hospitalbackend.model.vo.DiagnoseParam;
import com.neuedu.hospitalbackend.model.vo.PatientCaseTemplateParam;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 3.10病历模版管理
 * 提供了了模版管理服务方法，以及查询模版的方法
 * @author Steve
 */
public interface PatientCaseTemplateService {

    /**
     * 将当前页面中的内容保存到病历模板中
     * @param patientCaseTemplateParam 当前模板中的内容
     */
    CommonResult saveAsCaseTemplate(PatientCaseTemplateParam patientCaseTemplateParam);

    /**
     * 修改病历模板
     * @param patientCaseTemplateParam 模板修改后内容
     */
    CommonResult modifyPatientCaseTemplate(PatientCaseTemplateParam patientCaseTemplateParam);

    /**
     * 查询该医生所有可用模板
     * @param roleId 医生的角色id
     * @return 可用病历模版列表
     */
    CommonResult listPatientCaseTemplate(Integer roleId);

    /**
     * 删除病历模板
     * @param roleId,caseTemplateId
     */
    CommonResult deletePatientCaseTemplate(Integer roleId, String name);




    /**
     * 查看该病人的历史过往病历
     * @param patientId 病人的id
     * @return 该病人的所有历史病历
     */
    CommonResult listPastPatientCase(Integer patientId);



}
