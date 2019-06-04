package com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.TreatmentMapper;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.model.vo.ProjectPatientParam;
import com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice.TreatmentProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TreatmentProjectServiceImpl implements TreatmentProjectService {

    @Resource
    private TreatmentMapper treatmentMapper;
    /**
     * 患者查询
     * 输入患者病历号或姓名，可以查询到本科室（处置）的待登记患者列表
     * 无输入时 输出所有待登记患者列表
     * 动态查询
     * @param projectPatientParam: projectType, caseId, patientName
     * @return 待登记患者信息列表
     */
    @Override
    public JSONObject listPreparedPatientsByCaseIdOrName(ProjectPatientParam projectPatientParam){
        JSONObject returnObject = new JSONObject();
        Integer caseId = projectPatientParam.getCaseId();
        String patientName = projectPatientParam.getPatientName();

        //TODO: 检查状态为 已登记
        List<HashMap> patients = treatmentMapper.listPreparedPatientsByCaseIdOrName(caseId, patientName);
        //TODO： else 报错
        returnObject.put("patients", patients);
        return returnObject;
    }


    /**
     * 患者查询
     * 选择患者可以相应申请的项目明细
     * @param projectPatientParam: projectType, caseId, patientName
     * @return inspectionId, inspectionName, inspectionGMTCreate, t.status(是否已缴费), requirement
     */
    @Override
    public JSONObject listAppliedProjectsByCaseId(ProjectPatientParam projectPatientParam){
        //无该病历号
        JSONObject returnObject = new JSONObject();
        Integer caseId = projectPatientParam.getCaseId();

        List<HashMap> projects = treatmentMapper.listAppliedProjectsByCaseId(caseId);

        //TODO： else 报错
        //timestamp格式转换为datetime
        for(HashMap project: projects) {
            project.put("gmt_create", String.valueOf(project.get("gmt_create")).substring(0, 19)); //yyyy-MM-dd HH:mm:ss
            //status转换成文字
        }
        returnObject.put("projects", projects);
        return returnObject;
    }

    /**
     * 执行确认
     * 选中相应的患者，选中执行的项目，点击“执行确认”按钮，进行登记操作。
     * 只有已缴费的项目，才可以进行登记
     * 选中列表中项目开始登记，更新项目申请信息：状态更新、填写医技医生id
     * @param projectParam: projectType,collectionId,projectId,doctorRoleId
     * @return 改动数据库行数
     * */
    @Override
    public int checkInProject(ProjectParam projectParam){
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        Integer doctorRoleId = projectParam.getDoctorRoleId();
        //TODO：安全：检查用户权限
        int count = treatmentMapper.checkInProject(collectionId, projectId, doctorRoleId);
        //TODO： else 报错
        return count;
    }

    /**
     * 取消执行
     * 选中相应的患者，选中项目，点击“取消执行”按钮，进行取消操作。
     * 更新项目申请信息：改变状态
     * @param projectParam：projectType, collectionId, projectId
     */
    @Override
    public int cancelProject(ProjectParam projectParam){
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        //TODO：安全：检查用户权限
        int count = treatmentMapper.cancelProject(collectionId, projectId);
        return count;
    }

}
