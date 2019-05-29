package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.po.*;

import java.util.List;

public class RegistrationServiceImpl implements com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.RegistrationService {

    public Patient getPatient(String IdCard){
        //TODO: 通过身份证号查询是否有此患者，用String接受
        return null;
    }

    public List<User> listAvailableDoctors(JSONObject jsonObject){
        return null;
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
