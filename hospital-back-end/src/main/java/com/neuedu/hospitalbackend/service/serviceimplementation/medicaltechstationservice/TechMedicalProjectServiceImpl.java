package com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice;

import com.neuedu.hospitalbackend.model.dao.ExaminationMapper;
import com.neuedu.hospitalbackend.model.dao.InspectionMapper;
import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.model.po.Examination;
import com.neuedu.hospitalbackend.model.po.Inspection;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.model.vo.PatientParam;
import com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice.TechMedicalProjectService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class TechMedicalProjectServiceImpl implements TechMedicalProjectService {

    @Resource
    private InspectionMapper inspectionMapper;
    @Resource
    private ExaminationMapper examinationMapper;
    @Resource
    private TransactionLogMapper transactionLogMapper;

    @Override
   public CommonResult listPreparedPatientsByCaseIdOrDateOrName(PatientParam patientParam){

        Integer projectType = patientParam.getProjectType();
        Integer caseId = patientParam.getCaseId();
        String patientName = patientParam.getPatientName();
        Integer departmentId = patientParam.getDepartmentId();
        String chargeDateStr = patientParam.getChargeDateStr();

        List<HashMap> waitingPatients;
        if(projectType == 1){
            waitingPatients = inspectionMapper.listPreparedPatientsByCaseIdOrDateOrName(caseId, patientName, chargeDateStr, departmentId);
        }
        else if(projectType == 2)
            waitingPatients = examinationMapper.listPreparedPatientsByCaseIdOrDateOrName(caseId, patientName, chargeDateStr, departmentId);
        else
            return CommonResult.fail(ResultCode.E_801);//科室类型异常
        return CommonResult.success(waitingPatients);
   }

    @Override
    public CommonResult listAllProjectsByCaseId(PatientParam patientParam){

        Integer projectType = patientParam.getProjectType();
        Integer caseId = patientParam.getCaseId();
        Integer departmentId = patientParam.getDepartmentId();
        String chargeDateStr = patientParam.getChargeDateStr();
        List<HashMap> projects;

        //参数检查
        if(caseId == null)
            return CommonResult.fail(ResultCode.E_801);//病历号参数异常
        if (departmentId == null)
            return CommonResult.fail(ResultCode.E_801);//部门参数异常

        //列出项目列表(collectionId, projectId, projectName, transactionLogStatus, projectStatus)
        if(1 == projectType)
            projects = inspectionMapper.listAllProjectsByCaseId(caseId, chargeDateStr, departmentId);
        else if(2 == projectType)
            projects = examinationMapper.listAllProjectsByCaseId(caseId, chargeDateStr, departmentId);
        else
            return CommonResult.fail(ResultCode.E_801);//科室类型异常
        return CommonResult.success(projects);
    }

    @Override
    public CommonResult checkInProject(ProjectParam projectParam){
        Integer projectType = projectParam.getProjectType();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        Integer doctorRoleId = projectParam.getDoctorRoleId();
        Byte transactionLogStatus = projectParam.getTransactionLogStatus();
        Byte projectStatus = projectParam.getProjectStatus();

        //参数验证
        if (collectionId == null)
            CommonResult.fail(ResultCode.E_801);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(ResultCode.E_801);//项目参数异常
        if(doctorRoleId == null)
            return CommonResult.fail(ResultCode.E_801);//医技医生参数异常
        //确认已缴费
        if(2 != transactionLogStatus)
            return CommonResult.fail(ResultCode.E_804);//项目操作权限异常

        //登记项目
        //可登记项目
        int count = 0;
        if(transactionLogStatus == 2 && projectStatus == 2){
            if(1 == projectType)
                count = inspectionMapper.checkInProject(collectionId, projectId, doctorRoleId);
            else if(2 == projectType)
                count = examinationMapper.checkInProject(collectionId, projectId, doctorRoleId);
            else
                return CommonResult.fail(ResultCode.E_801);//科室类型异常
            if(count > 0)
                return CommonResult.success(count);
            else
                return CommonResult.fail();
        }
        else
            return CommonResult.fail();
    }

    @Override
    public CommonResult cancelProject(ProjectParam projectParam){
        Integer projectType = projectParam.getProjectType();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        Integer doctorRoleId = projectParam.getDoctorRoleId();
        Byte transactionLogStatus = projectParam.getTransactionLogStatus();
        Byte projectStatus = projectParam.getProjectStatus();

        //参数验证
        if (collectionId == null)
            return CommonResult.fail(ResultCode.E_801);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(ResultCode.E_801);//项目参数异常
        //确认已缴费
        if(2 != transactionLogStatus)
            return CommonResult.fail(ResultCode.E_804);//项目操作权限异常

        //取消执行
        int count = 0;
        if(transactionLogStatus == 2 && projectStatus == 2){
            if(1 == projectType)
                count = inspectionMapper.cancelProject(collectionId, projectId, doctorRoleId);
            else if(2 == projectType)
                count = examinationMapper.cancelProject(collectionId, projectId, doctorRoleId);
            else
                return CommonResult.fail(ResultCode.E_801);//科室类型异常
            if(count > 0)
                return CommonResult.success(count);
            else
                return CommonResult.fail();
        }
        else
            return CommonResult.fail();
    }


    @Override
    public CommonResult listCheckedInButNotRecordedProjects(ProjectParam projectParam){
        Integer projectType = projectParam.getProjectType();
        Integer departmentId = projectParam.getDepartmentId();
        String dateStr = projectParam.getChargeDateStr();

        //已登记项目列表
        if (1 == projectType) {
            List<Inspection> inspections = inspectionMapper.listCheckedInButNotRecordedProjects(departmentId, dateStr);
            return CommonResult.success(inspections);
        }
        else if (2 == projectType){
            List<Examination> examinations = examinationMapper.listCheckedInButNotRecordedProjects(departmentId, dateStr);
            return CommonResult.success(examinations);
        }
        else
            return CommonResult.fail(ResultCode.E_801);//科室类型异常
    }

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
            return CommonResult.fail(ResultCode.E_801);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(ResultCode.E_801);//项目参数异常
        if(resultDescription == null || advice == null)
            return CommonResult.fail(ResultCode.E_805);//缺少必填参数

        int count;
        if (1 == projectType)
            count = inspectionMapper.recordResult(collectionId, projectId, resultDescription, resultImage, advice);
        else if (2 == projectType)
            count = examinationMapper.recordResult(collectionId, projectId, resultDescription, resultImage, advice);
        else
            return CommonResult.fail(ResultCode.E_801);//科室类型异常
        return CommonResult.success(count);
    }

    public CommonResult confirmProject(ProjectParam projectParam){
        Integer projectType = projectParam.getProjectType();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();

        //参数验证
        if (collectionId == null)
            return CommonResult.fail(ResultCode.E_801);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(ResultCode.E_801);//项目参数异常

        int count;
        if (1 == projectType)
            count = inspectionMapper.updateStatus(collectionId, projectId, (byte) 5);
        else if (2 == projectType)
            count = examinationMapper.updateStatus(collectionId, projectId, (byte)5);
        else
            return CommonResult.fail(ResultCode.E_801);//科室类型异常
        return CommonResult.success(count);
    }
}