package com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice.TreatmentProjectService;

import java.util.List;

public class TreatmentProjectServiceImp implements TreatmentProjectService {


    /*---- 4.3 患者处置 ----*/
    /**
     * 4.3.1 患者查询
     * 输入患者病历号或姓名，可以查询到本科室（处置）的待诊患者列表
     * 动态查询
     * @param jsonObject:{caseId, patientName}
     * @return 待登记患者信息列表
     */
    public List<Patient> listPreparedPatientsByCaseIdOrName(JSONObject jsonObject){
        return null;
    }

    /**
     * 4.3.1 患者查询
     * 选择患者可以相应申请的项目明细
     * TODO: 根据前端传来的caseId(/patientId),查询该患者检查/检验项目清单id，项目id
     * TODO：根据项目id查找具体信息
     * @param caseId
     * @return treatment清单详细信息
     */
    public JSONObject listNotCheckedInProjectsByCaseId(Integer caseId){
        return null;
    }

    /**
     * 4.3.2 执行确认
     * 选中相应的患者，选中执行的项目，点击“执行确认”按钮，进行登记操作。
     * 注意：只有已缴费的项目，才可以进行登记
     * TODO：选中列表中项目开始登记，更新项目申请信息：状态更新、填写医技医生id
     * @param jsonObject:{treatmentId, projectId, 医技医生Id}
     */
    public void checkInProject(JSONObject jsonObject){

    }

    /**
     * 4.3.3 取消执行
     * 选中相应的患者，选中项目，点击“取消执行”按钮，进行取消操作。
     * 注意：一般情况不会进行取消操作
     * TODO: 选择列表中项目执行取消操作，更新项目申请信息：改变状态
     * @param jsonObject：{treatmentId, projectId}
     */
    public void cancelProject(JSONObject jsonObject){

    }

}
