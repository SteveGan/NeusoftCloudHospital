package com.neuedu.hospitalbackend.service.serviceimplementation.medicaltechstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.model.dao.TreatmentMapper;
import com.neuedu.hospitalbackend.model.vo.ProjectParam;
import com.neuedu.hospitalbackend.model.vo.PatientParam;
import com.neuedu.hospitalbackend.service.serviceinterface.medicaltechstationservice.TreatmentProjectService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class TreatmentProjectServiceImpl implements TreatmentProjectService {

    @Resource
    private TreatmentMapper treatmentMapper;
    @Resource
    private TransactionLogMapper transactionLogMapper;


    /**
     * 患者查询
     * 1.输入患者病历号或姓名，可以查询到本科室（处置）的待登记患者列表
     * 无输入时 输出所有待登记患者列表
     * 动态查询
     * @param patientParam: projectType, caseId, patientName
     * @return 待登记患者信息列表
     */
    @Override
    public CommonResult listPreparedPatientsByCaseIdOrName(PatientParam patientParam){
        JSONObject returnObject = new JSONObject();
        Integer caseId = patientParam.getCaseId();
        String patientName = patientParam.getPatientName();
        Integer departmentId = patientParam.getDepartmentId();
        Integer projectType = patientParam.getProjectType();

        //参数检查
        if (departmentId == null)
            CommonResult.fail(ResultCode.E_801);//部门参数异常
        if(3 != projectType)
            CommonResult.fail(ResultCode.E_801);//科室类型异常

        //查询患者列表
        List<HashMap> patients = treatmentMapper.listPreparedPatientsByCaseIdOrName(caseId, patientName,departmentId);
        returnObject.put("patients", patients);
        return CommonResult.success(returnObject);
    }


    /**
     * 患者查询
     * 2.选择患者可以相应申请的项目明细
     * @param patientParam: projectType, caseId, patientName
     * @return inspectionId, inspectionName, inspectionGMTCreate, t.status(是否已缴费), requirement
     */
    @Override
    public CommonResult listAppliedProjectsByCaseId(PatientParam patientParam){
        //无该病历号
        JSONObject returnObject = new JSONObject();
        Integer caseId = patientParam.getCaseId();
        Integer projectType = patientParam.getProjectType();
        Integer departmentId = patientParam.getDepartmentId();

        //参数检查
        if(caseId == null)
            CommonResult.fail(ResultCode.E_801);//病历号参数异常
        if (departmentId == null)
            CommonResult.fail(ResultCode.E_801);//部门参数异常
        if(3 != projectType)
            CommonResult.fail(ResultCode.E_801);//科室类型异常

        //查询项目列表
        List<HashMap> projects = treatmentMapper.listAppliedProjectsByCaseId(caseId,departmentId);

        //timestamp格式转换为datetime
        for(HashMap project: projects)
            project.put("gmt_create", String.valueOf(project.get("gmt_create")).substring(0, 19)); //yyyy-MM-dd HH:mm:ss

        returnObject.put("projects", projects);
        return CommonResult.success(returnObject);
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
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        Integer doctorRoleId = projectParam.getDoctorRoleId();
        Integer projectType = projectParam.getProjectType();

        //参数验证
        if (collectionId == null)
            CommonResult.fail(ResultCode.E_801);//申请参数异常
        if(projectId == null)
            CommonResult.fail(ResultCode.E_801);//项目参数异常
        if(doctorRoleId == null)
            CommonResult.fail(ResultCode.E_801);//医技医生参数异常
        if(3 != projectType)
            CommonResult.fail(ResultCode.E_801);//科室类型异常
        //确认已缴费
        if(4 != transactionLogMapper.selectStatusOfProject(collectionId, projectId))
            CommonResult.fail(ResultCode.E_804);//项目操作权限异常

        int count = treatmentMapper.checkInProject(collectionId, projectId, doctorRoleId);

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
        Integer collectionId = projectParam.getCollectionId();
        Integer projectId = projectParam.getProjectId();
        Integer projectType = projectParam.getProjectType();

        //参数验证
        if(3 != projectType)
            CommonResult.fail(ResultCode.E_801);//科室类型异常
        if (collectionId == null)
            CommonResult.fail(ResultCode.E_801);//申请参数异常
        if(projectId == null)
            CommonResult.fail(ResultCode.E_801);//项目参数异常

        //取消执行
        int count = treatmentMapper.cancelProject(collectionId, projectId);

        if(count > 0)
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }

}
