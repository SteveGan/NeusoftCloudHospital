package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.DiagnoseMapper;
import com.neuedu.hospitalbackend.model.dao.PatientCaseMapper;
import com.neuedu.hospitalbackend.model.po.Diagnose;
import com.neuedu.hospitalbackend.model.po.PatientCase;
import com.neuedu.hospitalbackend.model.vo.DiagnoseParam;
import com.neuedu.hospitalbackend.model.vo.PatientCaseParam;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.PreliminaryCaseService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import com.neuedu.hospitalbackend.util.SHAUtils;
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
    public CommonResult getPatientCaseInfo(Integer doctorRoleId, Integer caseId){
        JSONObject returnJson = new JSONObject();
        JSONArray traditionalDiagnose = new JSONArray();
        JSONArray modernDiagnose = new JSONArray();
        JSONArray finalTraditionalDiagnose = new JSONArray();
        JSONArray finalModernDiagnose = new JSONArray();

        //参数检验
        if(caseId == null)
            return CommonResult.fail(ResultCode.E_801);//病历号参数异常
        if(doctorRoleId == null)
            return CommonResult.fail(ResultCode.E_801);//医生角色参数异常

        //病历信息
        HashMap patientCase = patientCaseMapper.getPatientCaseInfo(caseId);
        if(patientCase == null)
            return CommonResult.fail(ResultCode.E_800);//参数异常
        returnJson.put("caseId", caseId);
        returnJson.put("status",  patientCase.get("status"));
        returnJson.put("narrate",  patientCase.get("narrate"));
        returnJson.put("curTreatCondition", patientCase.get("curTreatCondition"));
        returnJson.put("assistDiagnose",  patientCase.get("assistDiagnose"));
        returnJson.put("curDisease",  patientCase.get("curDisease"));
        returnJson.put("allergy",  patientCase.get("allergy"));
        returnJson.put("pastDisease",  patientCase.get("pastDisease"));
        returnJson.put("physicalCondition",  patientCase.get("physicalCondition"));

        //诊断信息
        List<HashMap> diagnoses = diagnoseMapper.listDiagnosesDetailByCaseId(caseId);
        if(diagnoses.size() != 0) {
            //诊断类型
            Integer type;
            if (String.valueOf(diagnoses.get(0).get("type")).equals("中医疾病"))
                type = 0;// 中医诊断疾病
            else
                type = 1;// 西医诊断疾病
            returnJson.put("diagnoseType", type);

            for(HashMap diagnose: diagnoses) {
                Boolean isFirstDiagnosed = (Boolean)diagnose.get("isFirstDiagnosed");
                if (type == 0) {//中医
                    if (isFirstDiagnosed == true)
                        traditionalDiagnose.add(diagnose);//中医初诊
                    else
                        finalTraditionalDiagnose.add(diagnose);//中医确诊
                }
                else{ //西医
                    if (isFirstDiagnosed == true)
                        modernDiagnose.add(diagnose);//西医初诊
                    else
                        finalModernDiagnose.add(diagnose);//西医确诊
                }
            }
        }
        returnJson.put("traditionalDiagnose", traditionalDiagnose);
        returnJson.put("modernDiagnose", modernDiagnose);
        returnJson.put("finalTraditionalDiagnose", finalTraditionalDiagnose);
        returnJson.put("finalModernDiagnose", finalModernDiagnose);
        return CommonResult.success(returnJson);
    }

    /**
     * 确诊诊断
     * @param patientCaseParam
     */
    public CommonResult finalDiagnose(PatientCaseParam patientCaseParam){
        int count;
        Integer caseId = patientCaseParam.getCaseId();
        //删除已有诊断
        diagnoseMapper.deleteFinalDiagnose(caseId);
        //新增确诊诊断
        Integer diagnoseType = patientCaseParam.getDiagnoseType();
        List<DiagnoseParam> diagnoseParams;
        if(diagnoseType == 0)
            diagnoseParams = patientCaseParam.getFinalTraditionalDiagnose();
        else if (diagnoseType == 1)
            diagnoseParams = patientCaseParam.getFinalModernDiagnose();
        else
            return CommonResult.fail(ResultCode.E_800);
        for(DiagnoseParam diagnoseParam : diagnoseParams) {
            Diagnose diagnose = new Diagnose();
            diagnose.setCaseId(caseId);
            diagnose.setIsFirstDiagnosed(false);
            diagnose.setDiseaseId(diagnoseParam.getIcdCode());
            diagnose.setStartTime(Date.valueOf(diagnoseParam.getStartTime()));
            diagnose.setDiseaseId(diagnose.getDiseaseId());
            count = diagnoseMapper.insertSelective(diagnose);
            if (count <= 0)
                return CommonResult.fail(ResultCode.E_802);
        }
        //病历状态更新为确诊
        count = patientCaseMapper.updatePatientCaseStatus(caseId, 4);//确诊
        if (count <= 0)
            return CommonResult.fail(ResultCode.E_802);

        return CommonResult.success(count);
    }


    /**
     * 暂存
     * 将当前病历首页中的内容暂时存入数据库
     * @param patientCaseParam 当前病历首页中的内容
     */
    @Override
    public  CommonResult savePresentPatientCase(PatientCaseParam patientCaseParam){
        return savePatientCase(patientCaseParam, 2, true); //病历暂存状态：2     诊断状态：初诊1
    }

    /**
     *  提交
     *  提交最终版病历首页中的内容保存到数据库, 注意：最终版本
     * @param patientCaseParam 当前病历首页中的内容
     * @return
     */
    @Override
    public CommonResult submitPresentPatientCase(PatientCaseParam patientCaseParam){
        return savePatientCase(patientCaseParam, 3, true); //病历已诊状态：3    诊断状态：确诊0
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
        String assistDiagnose = patientCaseParam.getAssistDiagnose();
        Integer diagnoseType = patientCaseParam.getDiagnoseType();
        List<DiagnoseParam> diagnoses;

        //参数检查
        if(caseId == null)
            return CommonResult.fail(ResultCode.E_801);//病历号参数异常
        //状态检查
        int curStatus = patientCaseMapper.getPatientCaseStatus(caseId);
//        if (1 != curStatus && 2 != curStatus)//待诊或暂存状态
//            return CommonResult.fail(ResultCode.E_804);//操作权限异常

        //暂存或提交
        //病历
        int count = patientCaseMapper.updatePatientCase(caseId, narrate, curDisease, curTreatCondition, pastDisease,
                allergy, physicalCondition, assistDiagnose, status);// 暂存状态status：2     已诊状态status：3
        if(count <= 0)
            return CommonResult.fail(ResultCode.E_802);//保存失败

        //诊断
        List<String> existedDiseaseIcdCodes = diagnoseMapper.listDiseaseIcdCodesByCaseId(caseId); //所有数据库暂存诊断
        if(diagnoseType == 0) {
            diagnoses = patientCaseParam.getTraditionalDiagnose();
        }
        else if(diagnoseType == 1) {
            diagnoses = patientCaseParam.getModernDiagnose();
        }
        else
            return CommonResult.fail(ResultCode.E_801);
        for(DiagnoseParam diagnoseParam: diagnoses) {
            //若数据库已存该诊断，且再次要求暂存/提交，则更新该诊断
            if (existedDiseaseIcdCodes.contains(diagnoseParam.getIcdCode())) {
                count = diagnoseMapper.updateExisted(diagnoseParam.getIcdCode(), diagnoseParam.getStartTime(), isFirstDiagnosed);
                if (count <= 0)
                    return CommonResult.fail(ResultCode.E_802);//保存失败
                existedDiseaseIcdCodes.remove(diagnoseParam.getIcdCode());
            }
            //若数据库不存在该诊断，要求暂存/提交，则增加该诊断
            else if (!existedDiseaseIcdCodes.contains(diagnoseParam.getIcdCode())) {
                Diagnose diagnose = new Diagnose();
                diagnose.setCaseId(caseId);
                diagnose.setDiseaseId(diagnoseParam.getIcdCode());
                diagnose.setStartTime(Date.valueOf(diagnoseParam.getStartTime()));
                diagnose.setIsFirstDiagnosed(isFirstDiagnosed);
                //插入数据库
                count = diagnoseMapper.insertSelective(diagnose);
                if (count <= 0)
                    return CommonResult.fail(ResultCode.E_802);//保存失败
            }
        }
        //若数据库已存该诊断，但不再暂存/提交，则删除该诊断
        if(!existedDiseaseIcdCodes.isEmpty()) {
            for (String leftDiseaseIcdCode : existedDiseaseIcdCodes) {
                count = diagnoseMapper.deleteByCaseIdAndDiseaseIcdCode(caseId, leftDiseaseIcdCode);
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
    public CommonResult clearPatientCase(Integer caseId) {
        //参数检查
        if(caseId == null)
            return CommonResult.fail(ResultCode.E_801);//病历号参数异常

        int count = 0;
        int curStatus = patientCaseMapper.getPatientCaseStatus(caseId);
        //暂存状态
        if (2 == curStatus){
            //清除病历首页信息
            count = patientCaseMapper.updatePatientCase(caseId, " ", " ", " ",
                    " ", " "," ", " ",1);//待诊状态：1
            if(count <= 0)
                return CommonResult.fail(ResultCode.E_802);//保存失败
            //删除诊断
            List<String> diseaseIcdCodes = diagnoseMapper.listDiseaseIcdCodesByCaseId(caseId);
            for(String diseaseIcdCode : diseaseIcdCodes) {
                count = diagnoseMapper.deleteByCaseIdAndDiseaseIcdCode(caseId, diseaseIcdCode);
            }
        }
        //待诊状态不操作数据库，其他状态操作异常
        else if(1 != curStatus) {
            return CommonResult.fail(ResultCode.E_804);//操作权限异常
        }

        return CommonResult.success(count);
    }

    /**
     * 确诊诊断信息
     * @param caseId
     */
    public CommonResult listFinalDiagnose(Integer caseId){
        JSONObject returnJson = new JSONObject();
        JSONArray finalTraditionalDiagnose = new JSONArray();
        JSONArray finalModernDiagnose = new JSONArray();

        List<HashMap> diagnoses = diagnoseMapper.listFinalDiagnoseByCaseId(caseId);
        for(HashMap diagnose : diagnoses){
            if((diagnose.get("type")).equals("中草药"))
                finalTraditionalDiagnose.add(diagnose);
            else
                finalModernDiagnose.add(diagnose);
        }

        returnJson.put("finalTraditionalDiagnose", finalTraditionalDiagnose);
        returnJson.put("finalModernDiagnose", finalModernDiagnose);

        return CommonResult.success(returnJson);
    }

    /**
     * 诊闭
     * @param caseId
     */
    @Override
    public CommonResult finishDiagnose(Integer caseId){
        int count = patientCaseMapper.updatePatientCaseStatus(caseId, 5);//诊闭状态
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
