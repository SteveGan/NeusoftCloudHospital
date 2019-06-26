package com.neuedu.hospitalbackend.service.serviceimplementation.finicialservice;

import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.DoctorStatisticsService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class DoctorStatisticsServiceImpl implements DoctorStatisticsService {

    @Resource
    private TransactionLogMapper transactionLogMapper;

    @Override
    public CommonResult doctorStatistics(String beginDateStr, String endDateStr){
        List<HashMap> result = convertMap(transactionLogMapper.calculateClinicianDoctorTotalMoney(beginDateStr, endDateStr));
        List<HashMap> visits = transactionLogMapper.countClinicianDoctorVisits(beginDateStr, endDateStr);
        List<HashMap> invoices = transactionLogMapper.countClinicianDoctorInvoices(beginDateStr, endDateStr);

        return  CommonResult.success(addInvoicesAndVisits(result, invoices, visits));
    }

    private List<HashMap> convertMap(List<HashMap> statistics){
        Map<String, HashMap> map = new HashMap<>();
        List<HashMap> result = new ArrayList<>();
        for(HashMap s: statistics){
            String userName = s.get("name").toString();
            String type = s.get("type").toString();
            BigDecimal totalMoney = (BigDecimal) s.get("total_money");
            if (!map.containsKey(userName)) {
                map.put(userName, new HashMap());
                map.get(userName).put("userName", userName);
                map.get(userName).put(type, totalMoney);
            } else{
                map.get(userName).put("userName", userName);
                map.get(userName).put(type, totalMoney);
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
            String userName = r.get("userName").toString();
            for(HashMap v: visits){
                if(userName.equals(v.get("name").toString())){
                    r.put("visits", v.get("visits")); //看诊人次
                    break;
                }
            }
            for(HashMap i: invoices){
                if(userName.equals(i.get("name").toString())){
                    r.put("invoiceAmount", i.get("invoice_amount")); //发票数量
                    break;
                }
            }
        }
        return result;
    }
}
