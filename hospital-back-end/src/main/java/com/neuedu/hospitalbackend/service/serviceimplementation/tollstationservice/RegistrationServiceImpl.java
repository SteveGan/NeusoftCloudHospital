package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.*;
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

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private PatientMapper patientMapper;

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
    public JSONObject makeRegistration(Registration registration, DoctorParam doctorParam, String idCard){
        JSONObject jsonObject = new JSONObject();
        //根据看诊医生和挂号级别，是否需要病历本，算出应收金额
        BigDecimal cost = registrationLevelMapper.getRegistrationLevelCostById(registration.getRegistrationLevelId());
        BigDecimal bookCost = new BigDecimal(1);
        BigDecimal totalCost = new BigDecimal(0);
        if (registration.getIsBuyCaseBook() == true)
            totalCost = cost.add(bookCost);
        else
            totalCost = cost;
        //向缴费表中添加新的缴费记录  --已缴费
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setRegistrationId(registration.getId());
        transactionLog.setPatientId(registration.getPatientId());
        transactionLog.setRoleId(registration.getCashierId());
        transactionLog.setType("挂号费");
        transactionLog.setAmount((short) 1);
        transactionLog.setPayType(registration.getPayType());
        transactionLog.setTotalMoney(totalCost);
        transactionLog.setStatus(new Byte((byte)2));
        int count = transactionLogMapper.insertSelective(transactionLog);
        if (count > 0){
            //向挂号表中添加新的挂号记录 --默认正常
            count = registrationMapper.insert(registration);
            if (count > 0) {
                //更新 所选医生 对应的余号数量
                count = arrangementMapper.updateRemainingAppointment(doctorParam.getAppointmentDateStr(), doctorParam.getRoleId());
                if (count > 0) {
                    Integer patientId = patientMapper.getPatientByIdCard(idCard);
                    if (patientId != null)
                        jsonObject.put("patientId", patientId);
                    else {

                    }

                }

            }
        }


        //检查患者是否已在本系统中
        //向病历表中添加新的病历记录 --默认待诊

        return jsonObject;
    }


}
