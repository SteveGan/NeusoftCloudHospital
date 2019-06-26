package com.neuedu.hospitalbackend.service.serviceimplementation.finicialservice;

import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.DepartmentStatisticsService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Polaris
 */
@Service
public class DepartmentStatisticsServiceImpl implements DepartmentStatisticsService {

    @Resource
    private TransactionLogMapper transactionLogMapper;

    public CommonResult clinicianDepartmentStatistics(String beginDateStr, String endDateStr){

/*        List<HashMap> statistics = new ArrayList();
        statistics.addAll(transactionLogMapper.calculateMoneyByDepartmentAndType(
                "examination", "project_id", "creator_role_id", beginDateStr, endDateStr));
        statistics.addAll(transactionLogMapper.calculateMoneyByDepartmentAndType(
                "inspection", "project_id", "creator_role_id", beginDateStr, endDateStr));
        statistics.addAll(transactionLogMapper.calculateMoneyByDepartmentAndType(
                "treatment", "project_id", "creator_role_id", beginDateStr, endDateStr));
        statistics.addAll(transactionLogMapper.calculateMoneyByDepartmentAndType(
                "recipe", "medicine_id", "creator_role_id", beginDateStr, endDateStr));
        List<HashMap> result = convertMap(statistics);
        List<HashMap> visits =transactionLogMapper.countPatientCasesByDepartmentName(beginDateStr, endDateStr);
        for(HashMap r: result){
            for(HashMap v: visits){
                if(r.get("departmentName").equals(v.get("departmentName"))){
                    r.put("visits", v.get("visits")); //看诊人次
                    break;
                }
            }

        }*/
        List<HashMap> result = convertMap(transactionLogMapper.calculateMoneyByDepartmentAndType(beginDateStr, endDateStr));
        List<HashMap> visits = transactionLogMapper.countPatientCasesByDepartmentName(beginDateStr, endDateStr);
        List<HashMap> invoices = transactionLogMapper.countInvoicesByDepartmentName(beginDateStr, endDateStr);
        for(HashMap r: result){
            String departmentName = r.get("departmentName").toString();
            for(HashMap v: visits){
                System.out.println(v.get("department_name").toString());
                if(departmentName.equals(v.get("department_name").toString())){
                    r.put("visits", v.get("visits")); //看诊人次
                    break;
                }
            }
            for(HashMap i: invoices){
                if(r.get("departmentName").equals(i.get("department_name").toString())){
                    r.put("invoiceAmount", i.get("invoice_amount")); //发票数量
                    break;
                }
            }
        }
        return  CommonResult.success(result);
    }

    public CommonResult technicianDepartmentStatistics(String beginDateStr, String endDateStr){
        List<HashMap> statistics = new ArrayList();
        /*statistics.addAll(transactionLogMapper.calculateMoneyByDepartmentAndType(
                "examination", "project_id", "examiner_role_id", beginDateStr, endDateStr));
        statistics.addAll(transactionLogMapper.calculateMoneyByDepartmentAndType(
                "inspection", "project_id", "inspector_role_id", beginDateStr, endDateStr));
        statistics.addAll(transactionLogMapper.calculateMoneyByDepartmentAndType(
                "treatment", "project_id", "treater_role_id", beginDateStr, endDateStr));
        statistics.addAll(transactionLogMapper.calculateMoneyByDepartmentAndType(
                "recipe", "medicine_id", "deliver_role_id", beginDateStr, endDateStr));

        List<HashMap> result = convertMap(statistics);*/

        return  CommonResult.success(statistics);
    }

    public List<HashMap> convertMap(List<HashMap> statistics){
        Map<String, HashMap> map = new HashMap<>();
        List<HashMap> result = new ArrayList<>();
        for(HashMap s: statistics){
            String departmentName = s.get("department_name").toString();
            String type = s.get("type").toString();
            BigDecimal totalMoney = (BigDecimal) s.get("total_money");
            if (!map.containsKey(departmentName)) {
                map.put(departmentName, new HashMap());
                map.get(departmentName).put("departmentName", departmentName);
                map.get(departmentName).put(type, totalMoney);
            } else{
                map.get(departmentName).put("departmentName", departmentName);
                map.get(departmentName).put(type, totalMoney);
            }
        }
        Iterator ite = map.entrySet().iterator();
        int i = 0;
        while(ite.hasNext()){
            Map.Entry string = (Map.Entry)ite.next();
            result.add(i, (HashMap) string.getValue());
            i++;
        }
        return result;
    }
}
