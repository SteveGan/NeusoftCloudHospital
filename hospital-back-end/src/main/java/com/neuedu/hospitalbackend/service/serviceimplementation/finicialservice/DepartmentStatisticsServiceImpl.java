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
        List<HashMap> result = convertMap(transactionLogMapper.calculateClinicianDepartmentsTotalMoney(beginDateStr, endDateStr, null));
        List<HashMap> visits = transactionLogMapper.countClinicianDepartmentsVisits(beginDateStr, endDateStr);
        List<HashMap> invoices = transactionLogMapper.countClinicianDepartmentsInvoices(beginDateStr, endDateStr, null);

        return  CommonResult.success(addInvoicesAndVisits(result, invoices, visits));
    }

    public CommonResult technicianDepartmentStatistics(String beginDateStr, String endDateStr){

        List<HashMap> statistics = new ArrayList<>();
        statistics.addAll(transactionLogMapper.calculateClinicianDepartmentsTotalMoney(beginDateStr, endDateStr, "挂号费"));
        statistics.addAll(transactionLogMapper.calculateExecutiveDepartmentsTotalMoney(
                "examination", "project_id", "examiner_role_id", beginDateStr, endDateStr));
        statistics.addAll(transactionLogMapper.calculateExecutiveDepartmentsTotalMoney(
                "inspection", "project_id", "inspector_role_id", beginDateStr, endDateStr));
        statistics.addAll(transactionLogMapper.calculateExecutiveDepartmentsTotalMoney(
                "treatment", "project_id", "treater_role_id", beginDateStr, endDateStr));
        statistics.addAll(transactionLogMapper.calculateExecutiveDepartmentsTotalMoney(
                "recipe", "medicine_id", "deliver_role_id", beginDateStr, endDateStr));

        List<HashMap> result = convertMap(statistics);

        List<HashMap> visits = new ArrayList<>();
        visits.addAll(transactionLogMapper.countClinicianDepartmentsVisits(beginDateStr, endDateStr));
        visits.addAll(transactionLogMapper.countExecutiveDepartmentsVisits(
                "examination", "project_id", "examiner_role_id", beginDateStr, endDateStr));
        visits.addAll(transactionLogMapper.countExecutiveDepartmentsVisits(
                "inspection", "project_id", "inspector_role_id", beginDateStr, endDateStr));
        visits.addAll(transactionLogMapper.countExecutiveDepartmentsVisits(
                "treatment", "project_id", "treater_role_id", beginDateStr, endDateStr));
        visits.addAll(transactionLogMapper.countExecutiveDepartmentsVisits(
                "recipe", "medicine_id", "deliver_role_id", beginDateStr, endDateStr));

        List<HashMap> invoices = new ArrayList<>();
        invoices.addAll(transactionLogMapper.countClinicianDepartmentsInvoices(beginDateStr, endDateStr, "挂号费"));
        invoices.addAll(transactionLogMapper.countExecutiveDepartmentsInvoices(
                "examination", "project_id", "examiner_role_id", beginDateStr, endDateStr));
        invoices.addAll(transactionLogMapper.countExecutiveDepartmentsInvoices(
                "inspection", "project_id", "inspector_role_id", beginDateStr, endDateStr));
        invoices.addAll(transactionLogMapper.countExecutiveDepartmentsInvoices(
                "treatment", "project_id", "treater_role_id", beginDateStr, endDateStr));
        invoices.addAll(transactionLogMapper.countExecutiveDepartmentsInvoices(
                "recipe", "medicine_id", "deliver_role_id", beginDateStr, endDateStr));

        return  CommonResult.success(addInvoicesAndVisits(result, invoices, visits));
    }

private List<HashMap> convertMap(List<HashMap> statistics){
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

    private List<HashMap> addInvoicesAndVisits(List<HashMap> result, List<HashMap> invoices, List<HashMap> visits){
        for(HashMap r: result){
            String departmentName = r.get("departmentName").toString();
            for(HashMap v: visits){
                if(departmentName.equals(v.get("department_name").toString())){
                    r.put("visits", v.get("visits")); //看诊人次
                    break;
                }
            }
            for(HashMap i: invoices){
                if(departmentName.equals(i.get("department_name").toString())){
                    r.put("invoiceAmount", i.get("invoice_amount")); //发票数量
                    break;
                }
            }
        }
        return result;
    }
}
