package com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Patient;

import java.util.List;

/**
 * 4. 检查项目医技工作站
 */
public interface techProjectService {

    /*---- 4.1 患者检查 ----*/
    /*---- 4.2 患者检验 （同检查）----*/
    /**
     * 4.1.1 患者查询
     * 输入患者病历号或姓名，可以查询到本科室（检查/检验）的待诊患者列表
     * 动态查询
     * @param jsonObject:{caseId, patientName, type(inspection or examination)}
     * @return 待登记患者信息列表
     */
    List<Patient> listPreparedPatientsByCaseIdOrName(JSONObject jsonObject);

    /**
     * 4.1.1 患者查询
     * 选择患者可以相应申请的项目明细
     * TODO: 根据前端传来的caseId(/patientId),查询该患者检查/检验项目清单id，项目id
     * TODO：根据项目id查找具体信息
     * @param jsonObject:{caseId, type(inspection or examination)}
     * @return Inspection or Examination
     */
    List<Object> listNotCheckedInProjectsByCaseId(JSONObject jsonObject);

    /**
     * 4.1.2 执行确认
     * 选中相应的患者，选中执行的项目，点击“执行确认”按钮，进行登记操作。
     * 注意：只有已缴费的项目，才可以进行登记
     * TODO：选中列表中项目开始登记，更新项目申请信息：状态更新、填写医技医生id
     * @param jsonObject:{inspectionId/examinationId, projectId, 医技医生Id}
     */
    void checkInProject(JSONObject jsonObject);

    /**
     * 4.1.3 取消执行
     * 选中相应的患者，选中项目，点击“取消执行”按钮，进行取消操作。
     * 注意：一般情况不会进行取消操作
     * TODO: 选择列表中项目执行取消操作，更新项目申请信息：改变状态
     * @param jsonObject：{inspectionId, projectId}
     */
    void cancelProject(JSONObject jsonObject);

    /**
     * 4.1.4 填写结果
     * 选中相应的患者和项目后，点击“结果录入”按钮，录入检查结果，如果检查项目有图片，上传检查结果图片
     * TODO: 显示所有已登记但未录入结果的项目
     * @param caseId
     */
    void listDoneAndNotRecordedProject(Integer caseId);

    /**
     * 4.1.4 填写结果
     * TODO: 选中项目，录入结果: 结果文字、图片（非必填）、医生建议
     * @param jsonObject:{caseId, resultDescription, resultPicture, advice}
     */
    void recordResult(JSONObject jsonObject);

}

