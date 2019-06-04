package com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice;

import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.model.vo.ProjectPatientParam;
import com.neuedu.hospitalbackend.util.CommonResult;


/**
 * 处置项目医技工作站
 */
public interface TreatmentProjectService {

    /**
     * 患者查询
     * 输入患者病历号或姓名，可以查询到本科室（检查/检验）的待诊患者列表
     * @param projectPatientParam: caseId, patientName
     * @return 待登记患者信息列表
     */
    CommonResult listPreparedPatientsByCaseIdOrName(ProjectPatientParam projectPatientParam);

    /**
     * 患者查询
     * 选择患者可以相应申请的项目明细
     * @param projectPatientParam: caseId, patientName
     * @return projectId, projectName, projectCollectionGMTCreate, t.status(是否已缴费), requirement
     */
    CommonResult listAppliedProjectsByCaseId(ProjectPatientParam projectPatientParam);

    /**
     * 执行确认
     * 选中相应的患者，选中执行的项目，点击“执行确认”按钮，进行登记操作。
     * 只有已缴费的项目，才可以进行登记
     * 更新项目申请信息：状态更新、填写医技医生id
     * @param projectParam：collectionId, projectId, doctorRoleId
     * @return 改动数据库行数
     */
    CommonResult checkInProject(ProjectParam projectParam);

    /**
     * 取消执行
     * 选中相应的患者，选中项目，点击“取消执行”按钮，进行取消操作。
     * 更新项目申请信息：改变状态
     * @param projectParam：collectionId, projectId
     */
    CommonResult cancelProject(ProjectParam projectParam);


}
