package com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.ExaminationMapper;
import com.neuedu.hospitalbackend.model.dao.InspectionMapper;
import com.neuedu.hospitalbackend.model.dao.PatientCaseMapper;
import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.model.vo.ProjectPatientParam;
import com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice.TechProjectService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
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
    @Resource
    private TransactionLogMapper transactionLogMapper;

    /**
     * 患者查询
     * 1.输入患者病历号或姓名，可以查询到本科室（检查/检验）的待登记患者列表
     * 无输入时 输出所有待登记患者列表
     * 动态查询
     * @param projectPatientParam: projectType, caseId, patientName
     * @return 待登记患者信息列表
     */
    @Override
    public CommonResult listPreparedPatientsByCaseIdOrName(ProjectPatientParam projectPatientParam){
        JSONObject returnObject = new JSONObject();
        Integer projectType = projectPatientParam.getProjectType();
        Integer caseId = projectPatientParam.getCaseId();
        String patientName = projectPatientParam.getPatientName();
        Integer departmentId = projectPatientParam.getDepartmentId();

        //参数检查
        if (departmentId == null)
            return CommonResult.fail(ResultCode.E_801);//部门参数异常

        //查询患者列表
        List<HashMap> patients = new ArrayList<>();
        if(1 == projectType)
            patients = inspectionMapper.listPreparedPatientsByCaseIdOrName(caseId, patientName, departmentId);
        else if(2 == projectType)
            patients = examinationMapper.listPreparedPatientsByCaseIdOrName(caseId, patientName, departmentId);
        else
            return CommonResult.fail(ResultCode.E_800);//科室类型异常
        returnObject.put("patients", patients);
        return CommonResult.success(returnObject);
    }


    /**
     * 患者查询
     * 2.选择患者可以相应申请的项目明细
     * @param projectPatientParam: projectType, caseId, patientName
     * @return inspectionId, inspectionName, inspectionGMTCreate, t.status(是否已缴费), requirement
     */
    @Override
    public CommonResult listAppliedProjectsByCaseId(ProjectPatientParam projectPatientParam){
        JSONObject returnObject = new JSONObject();
        List<HashMap> projects = new ArrayList<>();
        Integer projectType = projectPatientParam.getProjectType();
        Integer caseId = projectPatientParam.getCaseId();
        Integer departmentId = projectPatientParam.getDepartmentId();

        //参数检查
        if(caseId == null)
            return CommonResult.fail(ResultCode.E_801);//病历号参数异常
        if (departmentId == null)
            return CommonResult.fail(ResultCode.E_801);//部门参数异常

        //查询项目列表
        if(1 == projectType)
            projects = inspectionMapper.listAppliedProjectsByCaseId(caseId, departmentId);
        else if(2 == projectType)
            projects = examinationMapper.listAppliedProjectsByCaseId(caseId, departmentId);
        else
            return CommonResult.fail(ResultCode.E_800);//科室类型异常

        //timestamp格式转换为datetime
        for(HashMap project: projects)
            project.put("gmt_create", String.valueOf(project.get("gmt_create")).substring(0, 19)); //yyyy-MM-dd HH:mm:ss

        returnObject.put("projects", projects);
        return CommonResult.success(projects);
    }


    /**
     * 执行确认
     * 1.选中相应的患者，选中执行的项目，点击“执行确认”按钮，进行登记操作。
     * 只有已缴费的项目，才可以进行登记
     * 选中列表中项目开始登记，更新项目申请信息：状态更新、填写医技医生id
     * @param projectParam: projectType,collectionId,projectId,doctorRoleId
     * @return 改动数据库行数
     * */
    @Override
    public CommonResult checkInProject(ProjectParam projectParam){
        Integer projectType = projectParam.getProjectType();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        Integer doctorRoleId = projectParam.getDoctorRoleId();

        //参数验证
        if (collectionId == null)
            CommonResult.fail(ResultCode.E_807);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(ResultCode.E_806);//项目参数异常
        if(doctorRoleId == null)
            return CommonResult.fail(ResultCode.E_803);//医技医生参数异常
        //确认已缴费
        if(2 != transactionLogMapper.selectStatusOfProject(collectionId, projectId))
            return CommonResult.fail(ResultCode.E_804);//项目操作权限异常

        //登记项目
        int count;
        if(1 == projectType)
            count = inspectionMapper.checkInProject(collectionId, projectId, doctorRoleId);
        else if(2 == projectType)
            count = examinationMapper.checkInProject(collectionId, projectId, doctorRoleId);
        else
            return CommonResult.fail(ResultCode.E_800);//科室类型异常

        if(count > 0)
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }


    /**
     * 取消执行
     * 1.选中相应的患者，选中项目，点击“取消执行”按钮，进行取消操作。
     * 更新项目申请信息：改变状态
     * @param projectParam：projectType, collectionId, projectId
     */
    @Override
    public CommonResult cancelProject(ProjectParam projectParam){
        Integer projectType = projectParam.getProjectType();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();

        //参数验证
        if (collectionId == null)
            return CommonResult.fail(ResultCode.E_807);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(ResultCode.E_806);//项目参数异常
        //确认已缴费
        if(2 != transactionLogMapper.selectStatusOfProject(collectionId, projectId))
            return CommonResult.fail(ResultCode.E_804);//项目操作权限异常

        //取消执行
        int count;
        if(1 == projectType)
            count = inspectionMapper.cancelProject(collectionId, projectId);
        else if(2 == projectType)
            count = examinationMapper.cancelProject(collectionId, projectId);
        else
            return CommonResult.fail(ResultCode.E_800);//科室类型异常

        if(count > 0)
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }


    /**
     * 填写结果
     * 1.显示所有已登记但未录入结果的项目
     * @param projectPatientParam: projectType, caseId
     */
    @Override
    public CommonResult listCheckedInButNotRecordedProject(ProjectPatientParam projectPatientParam){
        JSONObject returnJson = new JSONObject();
        Integer caseId = projectPatientParam.getCaseId();
        Integer projectType = projectPatientParam.getProjectType();
        Integer departmentId = projectPatientParam.getDepartmentId();

        //参数检查
        if(caseId == null)
            return CommonResult.fail(ResultCode.E_801);//病历号参数异常
        if (departmentId == null)
            return CommonResult.fail(ResultCode.E_801);//部门参数异常

        //未录入结果项目列表
        List<HashMap> projects = new ArrayList<>();
        if (1 == projectType)
            projects = inspectionMapper.listCheckedInButNotRecordedProject(caseId, departmentId);
        else if (2 == projectType)
            projects = examinationMapper.listCheckedInButNotRecordedProject(caseId, departmentId);
        else
            return CommonResult.fail(ResultCode.E_800);//科室类型异常

        returnJson.put("projects", projects);
        return CommonResult.success(returnJson);
    }


    /**
     * 填写结果
     * 2.选中相应的患者和项目后，点击“结果录入”按钮，录入检查结果，如果检查项目有图片，上传检查结果图片
     * 录入结果: 结果文字、图片（非必填）、医生建议
     * @param projectParam:projectType, collectionId, projectId, resultDescription, resultImage, advice
     */
    @Override
    public CommonResult recordResult(ProjectParam projectParam){
        Integer projectType = projectParam.getProjectType();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        String resultDescription = projectParam.getResultDescription();
        String resultImage = projectParam.getResultImage();
        String advice = projectParam.getAdvice();

        //参数验证
        if (collectionId == null)
            return CommonResult.fail(ResultCode.E_807);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(ResultCode.E_806);//项目参数异常
        if(resultDescription == null || advice == null)
            return CommonResult.fail(ResultCode.E_805);//缺少必填参数

        int count;
        if (1 == projectType)
            count = inspectionMapper.recordResult(collectionId, projectId, resultDescription, resultImage, advice);
        else if (2 == projectType)
            count = examinationMapper.recordResult(collectionId, projectId, resultDescription, resultImage, advice);
        else
            return CommonResult.fail(ResultCode.E_800);//科室类型异常

        return CommonResult.success(count);
    }

}