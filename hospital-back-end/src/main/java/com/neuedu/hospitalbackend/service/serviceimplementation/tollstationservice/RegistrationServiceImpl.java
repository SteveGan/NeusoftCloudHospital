package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.ArrangementMapper;
import com.neuedu.hospitalbackend.model.dao.RegistrationLevelMapper;
import com.neuedu.hospitalbackend.model.dao.TransactionLogMapper;
import com.neuedu.hospitalbackend.model.vo.DoctorParam;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.model.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RegistrationServiceImpl implements com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.RegistrationService {

    @Autowired
    private ArrangementMapper arrangementMapper;

    @Autowired
    private RegistrationLevelMapper registrationLevelMapper;

    @Autowired
    private TransactionLogMapper transactionLogMapper;

    @Override
    public JSONObject listAvailableDoctors(RegistrationParam registrationParam){
        List<Arrangement> availableDoctors = arrangementMapper.listAvailableDoctors(registrationParam.getAppointmentDateStr(), registrationParam.getRegistrationLevelId(), registrationParam.getDepartmentId());
        for(Arrangement a: availableDoctors){
            System.out.println(a.getUserName());
        }
        JSONObject result = new JSONObject();
        result.put("availableDoctors", availableDoctors);
        return result;
    }

    @Override
    public JSONObject makeRegistration(Registration registration){
        JSONObject jsonObject = new JSONObject();
        //根据看诊医生和挂号级别，是否需要病历本，算出应收金额
        BigDecimal cost = registrationLevelMapper.getRegistrationLevelCostById(registration.getRegistrationLevelId());
        jsonObject.put("cost", cost);
        //向缴费表中添加新的缴费记录  --已缴费
        int count = transactionLogMapper.insert();


        return jsonObject;
    }

    @Override
    public int updateRemainingAppointment(DoctorParam doctorParam){
        System.out.println("service-roleId" + doctorParam.getRoleId());
        int count = arrangementMapper.updateRemainingAppointment(doctorParam.getAppointmentDateStr(), doctorParam.getRoleId());
        return count;
    }

    @Override
    public int calculateAmount(JSONObject jsonObject){
        return 0;
    }
    @Override
    public void insertRegistrationLog(Registration registration){

    }
    @Override
    public void insertPatientCaseLog(PatientCase patientCase){

    }
}
