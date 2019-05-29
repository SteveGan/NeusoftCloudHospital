package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.ArrangementMapper;
import com.neuedu.hospitalbackend.model.dao.PatientMapper;
import com.neuedu.hospitalbackend.model.dto.RegistrationParam;
import com.neuedu.hospitalbackend.model.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

@Service
public class RegistrationServiceImpl implements com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.RegistrationService {

    @Autowired
    private ArrangementMapper arrangementMapper;

    //TODO: 通过身份证号查询是否有此患者，用String接受
    public Patient getPatient(String idCard){
        return null;
    }

    public JSONObject listAvailableDoctors(RegistrationParam registrationParam){
        System.out.println("service" + registrationParam.getAppointmentDate());
        List<Arrangement> availableDoctors = arrangementMapper.listAvailableDoctors(registrationParam.getAppointmentDate(), registrationParam.getRegistrationLevelId(), registrationParam.getDepartmentId());
        for(Arrangement a: availableDoctors){
            System.out.println(a.getUserName());
        }
        JSONObject result = new JSONObject();
        result.put("availableDoctors", availableDoctors);
        return result;
    }

    public void updateRemainingAppointment(Arrangement arrangement){

    }

    public int calculateAmount(JSONObject jsonObject){
        return 0;
    }

    public void insertRegistrationLog(Registration registration){

    }

    public void insertPatientCaseLog(PatientCase patientCase){

    }
}
