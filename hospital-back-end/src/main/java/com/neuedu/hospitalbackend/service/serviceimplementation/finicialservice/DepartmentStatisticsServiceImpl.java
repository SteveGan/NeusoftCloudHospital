package com.neuedu.hospitalbackend.service.serviceimplementation.finicialservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.DepartmentStatisticsService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author Polaris
 */
@Service
public class DepartmentStatisticsServiceImpl implements DepartmentStatisticsService {

    @Resource
    private TransactionLogMapper transactionLogMapper;

    public CommonResult clinicianDepartmentStatistics(String beginDateStr, String endDateStr){
        JSONObject jsonObject = new JSONObject();
        List<HashMap> examinationStatistics = transactionLogMapper.calculateMoneyByDepartmentAndType(
                "examination", "project_id", "creator_role_id", beginDateStr, endDateStr);
        List<HashMap> inspectionStatistics = transactionLogMapper.calculateMoneyByDepartmentAndType(
                "inspection", "project_id", "creator_role_id", beginDateStr, endDateStr);
        List<HashMap> treatmentStatistics = transactionLogMapper.calculateMoneyByDepartmentAndType(
                "treatment", "project_id", "creator_role_id", beginDateStr, endDateStr);
        List<HashMap> recipeStatistics = transactionLogMapper.calculateMoneyByDepartmentAndType(
                "recipe", "medicine_id", "creator_role_id", beginDateStr, endDateStr);
        jsonObject.put("examinationStatistics", examinationStatistics);
        jsonObject.put("inspectionStatistics", inspectionStatistics);
        jsonObject.put("treatmentStatistics", treatmentStatistics);
        jsonObject.put("recipeStatistics", recipeStatistics);
        return  CommonResult.success(jsonObject);
    }

    public CommonResult technicianDepartmentStatistics(String beginDateStr, String endDateStr){
        JSONObject jsonObject = new JSONObject();
        List<HashMap> examinationStatistics = transactionLogMapper.calculateMoneyByDepartmentAndType(
                "examination", "project_id", "examiner_role_id", beginDateStr, endDateStr);
        List<HashMap> inspectionStatistics = transactionLogMapper.calculateMoneyByDepartmentAndType(
                "inspection", "project_id", "inspector_role_id", beginDateStr, endDateStr);
        List<HashMap> treatmentStatistics = transactionLogMapper.calculateMoneyByDepartmentAndType(
                "treatment", "project_id", "treater_role_id", beginDateStr, endDateStr);
        List<HashMap> recipeStatistics = transactionLogMapper.calculateMoneyByDepartmentAndType(
                "recipe", "medicine_id", "deliver_role_id", beginDateStr, endDateStr);
        jsonObject.put("examinationStatistics", examinationStatistics);
        jsonObject.put("inspectionStatistics", inspectionStatistics);
        jsonObject.put("treatmentStatistics", treatmentStatistics);
        jsonObject.put("recipeStatistics", recipeStatistics);
        return  CommonResult.success(jsonObject);
    }
}
