package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.ArrangementMapper;
import com.neuedu.hospitalbackend.model.vo.DoctorParam;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.model.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServiceImpl implements com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.RegistrationService {

    @Autowired
    private ArrangementMapper arrangementMapper;

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
    public int updateRemainingAppointment(DoctorParam doctorParam){
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
