package com.neuedu.hospitalbackend.service.serviceimplementation.finicialservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.finicialservice.DoctorStatisticsService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DoctorStatisticsServiceImpl implements DoctorStatisticsService {

    @Resource
    private TransactionLogMapper transactionLogMapper;

    @Override
    public CommonResult doctorStatistics(String beginDateStr, String endDateStr){
        List<HashMap> result = convertMap(transactionLogMapper.calculateClinicianDoctorsTotalMoney(beginDateStr, endDateStr));
        List<HashMap> visits = transactionLogMapper.countClinicianDoctorsVisits(beginDateStr, endDateStr);
        List<HashMap> invoices = transactionLogMapper.countClinicianDoctorsInvoices(beginDateStr, endDateStr);

        return  CommonResult.success(addInvoicesAndVisits(result, invoices, visits));
    }

    @Override
    public CommonResult doctorStatisticsByUserIdAndDate(String dateStr, Integer userId){
        JSONObject jsonObject = new JSONObject();
        String pastSevenDays = getPastDate(7, dateStr, false);
        String pastThirtyDays = getPastDate(1, dateStr, true);
        System.out.println("过去七天：" + pastSevenDays);
        System.out.println("过去一个月：" + pastThirtyDays);
        //当天
        List<HashMap> currentDayResult = convertMapForIndividualDoctor(transactionLogMapper.calculateClinicianDoctorTotalMoney(userId, dateStr, dateStr));
        List<HashMap> currentDayVisits = transactionLogMapper.countClinicianDoctorVisits(userId, dateStr, dateStr);
        List<HashMap> currentDayInvoices = transactionLogMapper.countClinicianDoctorInvoices(userId, dateStr, dateStr);
        jsonObject.put("currentDay", addInvoicesAndVisitsForIndividualDoctor(currentDayResult, currentDayInvoices, currentDayVisits));

        //一周
        List<HashMap> sevenDaysResult = convertMapForIndividualDoctor(transactionLogMapper.calculateClinicianDoctorTotalMoney(userId, pastSevenDays, dateStr));
        List<HashMap> sevenDaysVisits = transactionLogMapper.countClinicianDoctorVisits(userId, pastSevenDays, dateStr);
        List<HashMap> sevenDaysInvoices = transactionLogMapper.countClinicianDoctorInvoices(userId, pastSevenDays, dateStr);
        jsonObject.put("sevenDays", addInvoicesAndVisitsForIndividualDoctor(sevenDaysResult, sevenDaysInvoices, sevenDaysVisits));

        //一个月
        List<HashMap> thirtyDaysResult = convertMapForIndividualDoctor(transactionLogMapper.calculateClinicianDoctorTotalMoney(userId, pastThirtyDays, dateStr));
        List<HashMap> thirtyDaysVisits = transactionLogMapper.countClinicianDoctorVisits(userId, pastThirtyDays, dateStr);
        List<HashMap> thirtyDaysInvoices = transactionLogMapper.countClinicianDoctorInvoices(userId, pastThirtyDays, dateStr);
        jsonObject.put("thirtyDays", addInvoicesAndVisitsForIndividualDoctor(thirtyDaysResult, thirtyDaysInvoices, thirtyDaysVisits));

        return CommonResult.success(jsonObject);
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

    private List<HashMap> convertMapForIndividualDoctor(List<HashMap> statistics){
        Map<Integer, HashMap> map = new HashMap<>();
        List<HashMap> result = new ArrayList<>();
        for(HashMap s: statistics){
            Integer roleId = Integer.valueOf(s.get("id").toString());
            String type = s.get("type").toString();
            BigDecimal totalMoney = (BigDecimal) s.get("total_money");
            String departmentName = s.get("department_name").toString();
            String titleName = s.get("title_name").toString();
            if (!map.containsKey(roleId)) {
                map.put(roleId, new HashMap());
                map.get(roleId).put("roleId", roleId);
                map.get(roleId).put("departmentName", departmentName);
                map.get(roleId).put("titleName", titleName);
                map.get(roleId).put(type, totalMoney);
            } else{
                map.get(roleId).put("roleId", roleId);
                map.get(roleId).put(type, totalMoney);
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

    private List<HashMap> addInvoicesAndVisitsForIndividualDoctor(List<HashMap> result, List<HashMap> invoices, List<HashMap> visits){
        for(HashMap r: result){
            Integer roleId = Integer.valueOf(r.get("roleId").toString());
            for(HashMap v: visits){
                if(roleId == Integer.valueOf(v.get("id").toString())){
                    r.put("visits", v.get("visits")); //看诊人次
                    break;
                }
            }
            for(HashMap i: invoices){
                if(roleId == Integer.valueOf(i.get("id").toString())){
                    r.put("invoiceAmount", i.get("invoice_amount")); //发票数量
                    break;
                }
            }
        }
        return result;
    }

    private static String getPastDate(int days, String dateStr, boolean isMonth) {
        java.sql.Date date = java.sql.Date.valueOf(dateStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(isMonth)
            calendar.add(Calendar.MONTH, - days);
        else
            calendar.add(Calendar.DATE, - days);

        Date targetDay = calendar.getTime();
        String targetDayStr = sdf.format(targetDay);
        return targetDayStr;
    }

}
