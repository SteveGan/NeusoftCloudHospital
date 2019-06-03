package com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.ExaminationMapper;
import com.neuedu.hospitalbackend.model.dao.InspectionMapper;
import com.neuedu.hospitalbackend.model.vo.ProjectCheckInParam;
import com.neuedu.hospitalbackend.model.vo.ProjectPatientParam;
import com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice.TechProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TechProjectServiceImpl implements TechProjectService {

    @Resource
    private InspectionMapper inspectionMapper;
    @Resource
    private ExaminationMapper examinationMapper;

    /**
     * 4.1.1 患者查询
     * 输入患者病历号或姓名，可以查询到本科室（检查/检验）的待登记患者列表
     * 无输入时 输出所有待登记患者列表
     * 动态查询
     * @param projectPatientParam: projectType, caseId, patientName
     * @return 待登记患者信息列表
     */
    @Override
    public JSONObject listPreparedPatientsByCaseIdOrName(ProjectPatientParam projectPatientParam){
        JSONObject returnObject = new JSONObject();
        List<HashMap> patients = new ArrayList<>();
        String projectType = projectPatientParam.getProjectType();
        Integer caseId = projectPatientParam.getCaseId();
        String patientName = projectPatientParam.getPatientName();

        if(projectType.equals("检查"))
            patients = inspectionMapper.listPreparedInspectionPatientsByCaseIdOrName(caseId, patientName);
        else if(projectType.equals("检验"))
            patients = examinationMapper.listPreparedExaminationPatientsByCaseIdOrName(caseId, patientName);
        //TODO： else 报错

        returnObject.put("patients", patients);
        return returnObject;
    }


    /**
     * 4.1.1 患者查询
     * 选择患者可以相应申请的项目明细
     * @param projectPatientParam: projectType, caseId, patientName
     * @return inspectionId, inspectionName, inspectionGMTCreate, t.status(是否已缴费), requirement
     */
    @Override
    public JSONObject listAppliedProjectsByCaseId(ProjectPatientParam projectPatientParam){
        //无该病历号
        JSONObject returnObject = new JSONObject();
        List<HashMap> projects = new ArrayList<>();
        String projectType = projectPatientParam.getProjectType();
        Integer caseId = projectPatientParam.getCaseId();

        if(projectType.equals("检查"))
            projects = inspectionMapper.listAppliedProjectsByCaseId(caseId);
        else if(projectType.equals("检验"))
            projects = examinationMapper.listAppliedProjectsByCaseId(caseId);
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
     * 4.1.2 执行确认
     * 选中相应的患者，选中执行的项目，点击“执行确认”按钮，进行登记操作。
     * 注意：只有已缴费的项目，才可以进行登记
     * 选中列表中项目开始登记，更新项目申请信息：状态更新、填写医技医生id
     * @param projectCheckInParam: projectType,collectionId,projectId,doctorRoleId
     * @return 改动数据库行数
     * */
    @Override
    public int checkInProject(ProjectCheckInParam projectCheckInParam){
        Integer collectionId = projectCheckInParam.getCollectionId();
        Integer projectId = projectCheckInParam.getProjectId();
        Integer doctorRoleId = projectCheckInParam.getDoctorRoleId();
        String projectType = projectCheckInParam.getProjectType();
        System.out.println("projectType" + projectType);
        int count = 0;
        //TODO：安全：检查用户权限
        if(projectType.equals("检查"))
            count = inspectionMapper.checkInProject(collectionId, projectId, doctorRoleId);
        else if(projectType.equals("检验"))
            count = examinationMapper.checkInProject(collectionId, projectId, doctorRoleId);
        //TODO： else 报错
        return count;
    }

    /**
     * 4.1.3 取消执行
     * 选中相应的患者，选中项目，点击“取消执行”按钮，进行取消操作。
     * 注意：一般情况不会进行取消操作
     * TODO: 选择列表中项目执行取消操作，更新项目申请信息：改变状态
     * @param jsonObject：{inspectionId, projectId}
     */
    @Override
    public void cancelProject(JSONObject jsonObject){

    }

    /**
     * 4.1.4 填写结果
     * 选中相应的患者和项目后，点击“结果录入”按钮，录入检查结果，如果检查项目有图片，上传检查结果图片
     * TODO: 显示所有已登记但未录入结果的项目
     * @param caseId
     */
    @Override
    public void listDoneAndNotRecordedProject(Integer caseId){

    }

    /**
     * 4.1.4 填写结果
     * TODO: 选中项目，录入结果: 结果文字、图片（非必填）、医生建议
     * @param jsonObject:{caseId, resultDescription, resultPicture, advice}
     */
    @Override
    public void recordResult(JSONObject jsonObject){

    }

}