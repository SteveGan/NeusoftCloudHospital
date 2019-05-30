package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.ArrangementMapper;
import com.neuedu.hospitalbackend.model.dto.RegistrationParam;
import com.neuedu.hospitalbackend.model.po.Arrangement;
import com.neuedu.hospitalbackend.model.po.Patient;
import com.neuedu.hospitalbackend.model.po.PatientCase;
import com.neuedu.hospitalbackend.model.po.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServiceImpl implements com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.RegistrationService {

    @Autowired
    private ArrangementMapper arrangementMapper;

    public JSONObject listAvailableDoctors(RegistrationParam registrationParam){
        List<Arrangement> availableDoctors = arrangementMapper.listAvailableDoctors(registrationParam.getAppointmentDateStr(), registrationParam.getRegistrationLevelId(), registrationParam.getDepartmentId());
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
