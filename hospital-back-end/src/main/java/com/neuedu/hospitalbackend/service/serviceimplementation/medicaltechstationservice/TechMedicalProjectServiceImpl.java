package com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.DepartmentMapper;
import com.neuedu.hospitalbackend.model.dao.ExaminationMapper;
import com.neuedu.hospitalbackend.model.dao.InspectionMapper;
import com.neuedu.hospitalbackend.model.po.Examination;
import com.neuedu.hospitalbackend.model.po.Inspection;
import com.neuedu.hospitalbackend.model.po.Registration;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.model.vo.PatientParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.PatientService;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.ProjectCollectionManagementService;
import com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice.TechMedicalProjectService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_801;


@Service
public class TechMedicalProjectServiceImpl implements TechMedicalProjectService {

    @Autowired
    private InspectionMapper inspectionMapper;
    @Autowired
    private ExaminationMapper examinationMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ProjectCollectionManagementService projectCollectionManagementService;

    @Override
   public CommonResult listPreparedPatientsByCaseIdOrDateOrName(PatientParam patientParam){

        Integer caseId = patientParam.getCaseId();
        String patientName = patientParam.getPatientName();
        Integer departmentId = patientParam.getDepartmentId();
        String chargeDateStr = patientParam.getChargeDateStr();

        String projectType = departmentMapper.getClassificationById(departmentId);
        List<HashMap> waitingPatients;
        if(projectType.equals("医学影像科")){
            waitingPatients = inspectionMapper.listPreparedPatientsByCaseIdOrDateOrName(caseId, patientName, chargeDateStr, departmentId);
        }
        else if(projectType.equals("医技科"))
            waitingPatients = examinationMapper.listPreparedPatientsByCaseIdOrDateOrName(caseId, patientName, chargeDateStr, departmentId);
        else
            return CommonResult.fail(E_801);//科室类型异常
        return CommonResult.success(waitingPatients);
   }

    @Override
    public CommonResult listAllProjectsByCaseId(PatientParam patientParam){
        JSONObject jsonObject = new JSONObject();
        Integer caseId = patientParam.getCaseId();
        Integer departmentId = patientParam.getDepartmentId();
        String chargeDateStr = patientParam.getChargeDateStr();
        List<HashMap> projects;

        //参数检查
        if(caseId == null)
            return CommonResult.fail(E_801);//病历号参数异常
        if (departmentId == null)
            return CommonResult.fail(E_801);//部门参数异常
        //查询患者相关信息
        Registration registration = (Registration) patientService.getRegistrationInfo(caseId).getData();
        jsonObject.put("patientInfo", registration);

        //查询患者项目列表(collectionId, projectId, projectName, transactionLogStatus, projectStatus)
        String projectType = departmentMapper.getClassificationById(departmentId);
        if(projectType.equals("医学影像科")){
            projects = inspectionMapper.listAllProjectsByCaseId(caseId, chargeDateStr, departmentId);
        }
        else if(projectType.equals("医技科"))
            projects = examinationMapper.listAllProjectsByCaseId(caseId, chargeDateStr, departmentId);
        else
            return CommonResult.fail(E_801);//科室类型异常
        jsonObject.put("projects", projects);
        return CommonResult.success(jsonObject);
    }

    @Override
    public CommonResult listItemsByCollectionIdAndProjectId(ProjectParam projectParam){
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();

        List<HashMap> projectDetails;
        if(collectionId.toString().startsWith("2")){
            projectDetails = inspectionMapper.listItems(collectionId, projectId);
        }
        else if(collectionId.toString().startsWith("3")){
            projectDetails= examinationMapper.listItems(collectionId, projectId);
        }
        else
            return CommonResult.fail(E_801);
        return CommonResult.success(projectDetails);
    }


    @Override
    public CommonResult checkInProject(ProjectParam projectParam){

        Integer departmentId = projectParam.getDepartmentId();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        Integer doctorRoleId = projectParam.getDoctorRoleId();
        Byte transactionLogStatus = projectParam.getTransactionLogStatus();
        Byte projectStatus = projectParam.getProjectStatus();

        //参数验证
        if (collectionId == null)
            CommonResult.fail(E_801);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(E_801);//项目参数异常
        if(doctorRoleId == null)
            return CommonResult.fail(E_801);//医技医生参数异常
        //确认已缴费
        if(2 != transactionLogStatus)
            return CommonResult.fail(ResultCode.E_804);//项目操作权限异常

        //登记项目
        //可登记项目
        int count = 0;
        if(transactionLogStatus == 2 && projectStatus == 2){
            String projectType = departmentMapper.getClassificationById(departmentId);
            if(projectType.equals("医学影像科"))
                count = inspectionMapper.checkInProject(collectionId, projectId, doctorRoleId);
            else if(projectType.equals("医技科"))
                count = examinationMapper.checkInProject(collectionId, projectId, doctorRoleId);
            else
                return CommonResult.fail(E_801);//科室类型异常
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

        Integer departmentId = projectParam.getDepartmentId();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        Integer doctorRoleId = projectParam.getDoctorRoleId();
        Byte transactionLogStatus = projectParam.getTransactionLogStatus();
        Byte projectStatus = projectParam.getProjectStatus();

        //参数验证
        if (collectionId == null)
            return CommonResult.fail(E_801);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(E_801);//项目参数异常
        //确认已缴费
        if(2 != transactionLogStatus)
            return CommonResult.fail(ResultCode.E_804);//项目操作权限异常

        //取消执行
        int count = 0;
        if(transactionLogStatus == 2 && projectStatus == 2){
            String projectType = departmentMapper.getClassificationById(departmentId);
            if(projectType.equals("医学影像科"))
                count = inspectionMapper.cancelProject(collectionId, projectId, doctorRoleId);
            else if(projectType.equals("医技科"))
                count = examinationMapper.cancelProject(collectionId, projectId, doctorRoleId);
            else
                return CommonResult.fail(E_801);//科室类型异常
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

        Integer departmentId = projectParam.getDepartmentId();
        String dateStr = projectParam.getChargeDateStr();

        String projectType = departmentMapper.getClassificationById(departmentId);
        //已登记项目列表
        if (projectType.equals("医学影像科")) {
            List<Inspection> inspections = inspectionMapper.listCheckedInButNotRecordedProjects(departmentId, dateStr);
            return CommonResult.success(inspections);
        }
        else if (projectType.equals("医技科")){
            List<Examination> examinations = examinationMapper.listCheckedInButNotRecordedProjects(departmentId, dateStr);
            return CommonResult.success(examinations);
        }
        else
            return CommonResult.fail(E_801);//科室类型异常
    }

    @Override
    public CommonResult recordResult(ProjectParam projectParam){

        JSONObject jsonObject = new JSONObject();
        Integer caseId = projectParam.getCaseId();
        Integer departmentId = projectParam.getDepartmentId();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        String resultDescription = projectParam.getResultDescription();
        String resultImage = projectParam.getResultImage();
        String advice = projectParam.getAdvice();

        //参数验证
        if (collectionId == null)
            return CommonResult.fail(E_801);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(E_801);//项目参数异常
        if(resultDescription == null || advice == null)
            return CommonResult.fail(ResultCode.E_805);//缺少必填参数

        //更新项目结果
        int count;
        String projectType = departmentMapper.getClassificationById(departmentId);
        if (projectType.equals("医学影像科"))
            count = inspectionMapper.updateResult(collectionId, projectId, resultDescription, resultImage, advice);
        else if (projectType.equals("医技科"))
            count = examinationMapper.updateResult(collectionId, projectId, resultDescription, resultImage, advice);
        else
            return CommonResult.fail(E_801);//科室类型异常
        jsonObject.put("count", count);
        return CommonResult.success(jsonObject);
    }

    public CommonResult confirmProject(ProjectParam projectParam){

        Integer departmentId = projectParam.getDepartmentId();
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();

        //参数验证
        if (collectionId == null)
            return CommonResult.fail(E_801);//申请参数异常
        if(projectId == null)
            return CommonResult.fail(E_801);//项目参数异常

        int count;
        String projectType = departmentMapper.getClassificationById(departmentId);
        if (projectType.equals("医学影像科"))
            count = inspectionMapper.updateStatus(collectionId, projectId, (byte) 5);
        else if (projectType.equals("医技科"))
            count = examinationMapper.updateStatus(collectionId, projectId, (byte)5);
        else
            return CommonResult.fail(E_801);//科室类型异常
        return CommonResult.success(count);
    }
}