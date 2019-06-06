package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.DiagnoseMapper;
import com.neuedu.hospitalbackend.model.dao.DiseaseMapper;
import com.neuedu.hospitalbackend.model.dao.PatientCaseMapper;
import com.neuedu.hospitalbackend.model.po.Diagnose;
import com.neuedu.hospitalbackend.model.po.Disease;
import com.neuedu.hospitalbackend.model.po.PatientCase;
import com.neuedu.hospitalbackend.model.vo.DiagnoseParam;
import com.neuedu.hospitalbackend.model.vo.PatientCaseParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.PreliminaryCaseService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
public class PreliminaryCaseServiceImpl implements PreliminaryCaseService {

    @Resource
    private PatientCaseMapper patientCaseMapper;
    @Resource
    private DiagnoseMapper diagnoseMapper;
    @Resource
    private DiseaseMapper diseaseMapper;


    /**
     * 待诊、已诊患者列表
     * @param doctorRoleId
     * @return list of patient
     */
    @Override
    public CommonResult listPatients(Integer doctorRoleId){
        JSONObject returnObject = new JSONObject();

        //查询待诊患者
        List<HashMap> patients = patientCaseMapper.listWaitingPatients(doctorRoleId);
        for(HashMap patient : patients) { //计算年龄
            int age = getAgeByBirth((Date)patient.get("birthday"));
            patient.put("age", age);
        }
        returnObject.put("waitingPatients", patients);

        //查询已诊患者
        patients = patientCaseMapper.listWaitedPatients(doctorRoleId);
        for(HashMap patient : patients) { //计算年龄
            int age = getAgeByBirth((Date)patient.get("birthday"));
            patient.put("age", age);
        }
        returnObject.put("diagnosedPatients", patients);

        return CommonResult.success(returnObject);
    }

    /**
     * 用户病历信息
     * @param caseId
     * @return patientCase
     */
    @Override
    public CommonResult getPatientCaseContents(Integer doctorRoleId, Integer caseId){
        JSONObject returnJson = new JSONObject();

        if(caseId == null)
            return CommonResult.fail(ResultCode.E_801);//病历号参数异常
        if(doctorRoleId == null)
            return CommonResult.fail(ResultCode.E_801);//医生角色参数异常

        //病历
        PatientCase patientCase = patientCaseMapper.getPatientCase(caseId);
        if(doctorRoleId.compareTo(patientCase.getRoleId()) == 1)
            return CommonResult.fail(ResultCode.E_804);//权限异常
        returnJson.put("patientCase", patientCase);
        //诊断
        List<Diagnose> diagnoses = diagnoseMapper.listDiagnosesByCaseId(caseId);
        returnJson.put("diagnoses", diagnoses);
        String type = diseaseMapper.getTypeOfDiseaseById(diagnoses.get(0).getDiseaseId());
        if(type.equals("中医疾病"))
            returnJson.put("diagnoseType", "中医诊断");//中医
        else
            returnJson.put("diagnoseType", "西医诊断");//西医

        return CommonResult.success(returnJson);
    }

    /**
     * 暂存
     * 将当前病历首页中的内容暂时存入数据库
     * @param patientCaseParam 当前病历首页中的内容
     */
    @Override
    public  CommonResult savePresentPatientCase(PatientCaseParam patientCaseParam){
        return savePatientCase(patientCaseParam, 2, true); //暂存状态status：2     诊断状态：初诊1
    }

    /**
     *  提交
     *  提交最终版病历首页中的内容保存到数据库, 注意：最终版本
     * @param patientCaseParam 当前病历首页中的内容
     * @return
     */
    @Override
    public CommonResult submitPresentPatientCase(PatientCaseParam patientCaseParam){
        return savePatientCase(patientCaseParam, 3, false); //已诊状态status：3    诊断状态：确诊0
    }

    /**
     * 保存病历信息
     */
    public CommonResult savePatientCase(PatientCaseParam patientCaseParam, int status, Boolean isFirstDiagnosed){
        Integer caseId = patientCaseParam.getCaseId();
        String narrate = patientCaseParam.getNarrate();
        String curDisease = patientCaseParam.getCurDisease();
        String curTreatCondition = patientCaseParam.getCurTreatCondition();
        String pastDisease = patientCaseParam.getPastDisease();
        String allergy = patientCaseParam.getAllergy();
        String physicalCondition = patientCaseParam.getPhysicalCondition();
        List<DiagnoseParam> diagnoses = patientCaseParam.getDiagnoses();

        //参数检查
        if(caseId == null)
            return CommonResult.fail(ResultCode.E_801);//病历号参数异常
        int curStatus = patientCaseMapper.getPatientCaseStatus(caseId);
        if (4 == curStatus || 5 == curStatus)//已确诊或诊闭状态
            return CommonResult.fail(ResultCode.E_804);//操作权限异常

        //暂存或提交 病历
        int count = patientCaseMapper.savePatientCase(narrate, curDisease, curTreatCondition, pastDisease,
                allergy, physicalCondition, status);// 暂存状态status：2     已诊状态status：3
        if(count <= 0)
            return CommonResult.fail(ResultCode.E_802);//保存失败

        //暂存或提交 诊断
        List<Integer> existedDiseaseIds = diagnoseMapper.listDiseaseIdsByCaseId(caseId); //所有数据库暂存诊断
        for(DiagnoseParam diagnoseParam: diagnoses){
            //若数据库已存该诊断，且再次要求暂存/提交，则更新该诊断
            if(existedDiseaseIds.contains(diagnoseParam.getDiseaseId())){
                count = diagnoseMapper.updateExisted(diagnoseParam.getDiseaseId(), diagnoseParam.getStartTimeStr(), isFirstDiagnosed);
                if(count <= 0)
                    return CommonResult.fail(ResultCode.E_802);//保存失败
                existedDiseaseIds.remove(diagnoseParam.getDiseaseId());
            }
            //若数据库不存在该诊断，要求暂存/提交，则增加该诊断
            else if (!existedDiseaseIds.contains(diagnoseParam.getDiseaseId())){
                Diagnose diagnose = new Diagnose();
                diagnose.setCaseId(caseId);
                diagnose.setDiseaseId(diagnoseParam.getDiseaseId());
                diagnose.setStartTime(Date.valueOf(diagnoseParam.getStartTimeStr()));
                diagnose.setIsFirstDiagnosed(isFirstDiagnosed);
                //插入数据库
                count = diagnoseMapper.insertSelective(diagnose);
                if(count <= 0)
                    return CommonResult.fail(ResultCode.E_802);//保存失败
            }
            //若数据库已存该诊断，但不再暂存/提交，则删除该诊断
            else{
                for(Integer leftDiseaseId: existedDiseaseIds){
                    count = diagnoseMapper.deleteByDiseaseId(leftDiseaseId);
                    if(count <= 0)
                        return CommonResult.fail(ResultCode.E_803);//删除失败
                }
            }
        }
        return CommonResult.success(count);
    }

    /**
     * 清屏
     * 清除本病历信息以及诊断
     * @param caseId
     * @return
     */
    public CommonResult clearPatientCase(Integer caseId){

        //辅助检查
        int count = patientCaseMapper.savePatientCase(null, null, null,
                null, null,null, 1);//状态：待诊
        if(count <= 0)
            return CommonResult.fail(ResultCode.E_802);//保存失败

        List<Integer> diseaseIds = diagnoseMapper.listDiseaseIdsByCaseId(caseId);
        for(Integer diseaseId : diseaseIds) {
            count = diagnoseMapper.deleteByDiseaseId(diseaseId);
            if(count <= 0)
                return CommonResult.fail(ResultCode.E_803);//删除失败
        }
        //回滚...

        return CommonResult.success(count);
    }

    /**
     * 出生日期转为年龄
     * @param birthDay
     * @return age
     */
    public int getAgeByBirth(Date birthDay){
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }


}
