package com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice;

import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.model.vo.PatientParam;
import com.neuedu.hospitalbackend.util.CommonResult;

/**
 * 检查项目医技工作站
 */
public interface TechMedicalProjectService {

    /**
     * 查询到本科室（检查/检验）的待诊患者列表
     * @param patientParam 患者相关参数（病历号，科室...）
     * @return 待诊患者列表
     */
    CommonResult listPreparedPatientsByCaseIdOrDateOrName(PatientParam patientParam);

    /**
     * 患者查询
     * 查询指定患者所有的项目及状态及患者信息
     * @param patientParam: projectType, caseId, patientName
     * @return projectId, projectName, projectCollectionGMTCreate, t.status(是否已缴费), requirement
     */
    CommonResult listAllProjectsByCaseId(PatientParam patientParam);

    /**
     * 列出患者指定项目下的小项信息
     * @param projectParam: collectionId, projectId
     * @return 项目详情
     */
    CommonResult listItemsByCollectionIdAndProjectId(ProjectParam projectParam);

    /**
     * 执行确认（登记项目）
     * 选中相应的患者，选中执行的项目，点击“执行确认”按钮，进行登记操作。
     * 只有已缴费的项目，才可以进行登记
     * 更新项目申请信息：状态更新、填写医技医生id
     * @param projectParam：projectType, collectionId, projectId, doctorRoleId
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

    /**
     * 显示本科室该天所有已登记的项目
     * @param projectParam: departmentId, chargeDate
     * @return 已登记项目列表
     */
    CommonResult listCheckedInButNotRecordedProjects(ProjectParam projectParam);

    /**
     * 填写结果
     * 选中相应的患者和项目后，点击“结果录入”按钮，录入检查结果，如果检查项目有图片，上传检查结果图片
     * 录入结果: 结果文字、图片（非必填）、医生建议
     * @param projectParam:projectCollectionId, projectId, resultDescription, resultImage, advice
     */
    CommonResult recordResult(ProjectParam projectParam);

    /**
     * 执行完毕项目后，更新其状态
     */
    CommonResult confirmProject(ProjectParam projectParam);
}
