package com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice;


import com.neuedu.hospitalbackend.model.vo.PatientCaseParam;
import com.neuedu.hospitalbackend.util.CommonResult;



/**
 * 3.1  门诊病历首页
 * 包含关于门诊病历首页的service方法
 * @author Steve
 */
public interface PreliminaryCaseService {


    /**
     *  该医生所有患者列表：待诊、已诊
     * @param doctorRoleId
     * @return list of patient
     */
    CommonResult listPatients(Integer doctorRoleId);

    /**
     *  患者病历
     * @param doctorRoleId,caseId
     * @return list of patient
     */
    CommonResult getPatientCaseInfo(Integer doctorRoleId, Integer caseId);

    /**
     * 确诊诊断
     * @param patientCaseParam
     */
    CommonResult finalDiagnose(PatientCaseParam patientCaseParam);

    /**
     *  暂存
     *  将当前病历首页中的内容暂时存入数据库
     * @param patientCaseParam 当前病历首页中的内容
     * @return
     */
    CommonResult savePresentPatientCase(PatientCaseParam patientCaseParam);

    /**
     *  提交
     *  提交最终版病历首页中的内容保存到数据库, 注意：最终版本
     * @param patientCaseParam 当前病历首页中的内容
     * @return
     */
    CommonResult submitPresentPatientCase(PatientCaseParam patientCaseParam);

    /**
     * 清屏
     * 清除本病历信息以及诊断
     * @param caseId
     * @return
     */
    CommonResult clearPatientCase(Integer caseId);



}
