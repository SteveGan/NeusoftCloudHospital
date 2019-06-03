package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.vo.DoctorParam;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.model.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
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

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private PatientCaseMapper patientCaseMapper;

    @Override
    public synchronized JSONObject getNextRegistrationId(){
        Integer nextId = registrationMapper.getNextId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nextId", nextId);
        return jsonObject;
    }

    @Override
    public JSONObject listAvailableDoctors(RegistrationParam registrationParam){
        List<Arrangement> availableDoctors = arrangementMapper.listAvailableDoctors(registrationParam.getAppointmentDateStr(), registrationParam.getRegistrationLevelId(), registrationParam.getDepartmentId());
        for(Arrangement a: availableDoctors){
            System.out.println(a.getUserName());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("availableDoctors", availableDoctors);
        return jsonObject;
    }

    @Override
    public JSONObject calculateTotalFee(RegistrationParam registrationParam){
        //根据看诊医生和挂号级别，是否需要病历本，算出应收金额
        Short registrationLevelId = registrationParam.getRegistrationLevelId();
        BigDecimal cost = registrationLevelMapper.getRegistrationLevelCostById(registrationLevelId);
        BigDecimal bookCost = new BigDecimal(1);
        BigDecimal totalCost;
        if (registrationParam.getBuyCaseBook() == true)
            totalCost = cost.add(bookCost);
        else
            totalCost = cost;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalFee", totalCost);
        return jsonObject;
    }

    @Override
    public JSONObject makeRegistration(RegistrationParam registrationParam){
        JSONObject jsonObject = new JSONObject();
        //向缴费表中添加新的缴费记录  --已缴费
        TransactionLog transactionLog = new TransactionLog();
        int count = 0;
        synchronized (this) {
            //通过查询invoice表得到新的缴费记录的发票号
            String invoice_id = invoiceMapper.getAvailableInvoiceCode();
            //向缴费表中添加新的缴费记录  --已缴费
            transactionLog.setInvoiceCode(invoice_id);
            transactionLog.setRegistrationId(registrationParam.getRegistrationId());
            transactionLog.setPatientId(registrationParam.getPatientId());
            transactionLog.setRoleId(registrationParam.getCashierId());
            transactionLog.setType("挂号费");
            transactionLog.setAmount((short)(1));
            transactionLog.setPayType(registrationParam.getPayType());
            transactionLog.setTotalMoney(registrationParam.getTotalFee());
            transactionLog.setStatus((byte)2);
            count += transactionLogMapper.insertSelective(transactionLog);
        }

        if (count > 0){
            //向挂号表中添加新的挂号记录 --默认正常
            Registration registration = new Registration();
            registration.setPatientId(registrationParam.getPatientId());
            registration.setAppointmentDate(Date.valueOf(registrationParam.getAppointmentDateStr()));
            registration.setRoleId(registrationParam.getRoleId());
            registration.setRegistrationLevelId(registrationParam.getRegistrationLevelId());
            registration.setDepartmentId(registrationParam.getDepartmentId());
            registration.setTotalFee(registrationParam.getTotalFee());
            registration.setCashierId(registrationParam.getCashierId());
            registration.setPayType(registrationParam.getPayType());
            registration.setIsBuyCaseBook(registrationParam.getBuyCaseBook());
            count += registrationMapper.insertSelective(registration);
            if (count > 0) {
                //更新 所选医生 对应的余号数量
                count += arrangementMapper.updateRemainingAppointment(registrationParam.getAppointmentDateStr(), registrationParam.getTimeSlot(), registrationParam.getRoleId(), registrationParam.getRegistrationLevelId());
                if (count > 0) {
                    //检查患者是否已在本系统中
                    Integer patientId = patientMapper.getPatientByIdCard(registrationParam.getIdCard());
                    if (patientId != null)
                        jsonObject.put("patientId", patientId);
                    else {
                        Patient patient = new Patient();
                        patient.setIdCard(registrationParam.getIdCard());
                        patient.setAddress(registrationParam.getAddress());
                        patient.setGender(registrationParam.getGender());
                        patient.setName(registrationParam.getName());
                        patient.setBirthday(Date.valueOf(registrationParam.getBirthdayStr()));
                        count += patientMapper.insertSelective(patient);
                        jsonObject.put("result", "success");
                    }
                    //向病历表中添加新的病历记录 --默认待诊
                    PatientCase patientCase = new PatientCase();
                    patientCase.setRegistrationId(registrationParam.getRegistrationId());
                    patientCase.setPatientId(registrationParam.getPatientId());
                    patientCase.setRoleId(registration.getRoleId());
                    count += patientCaseMapper.insertSelective(patientCase);
                }
            }
        }
        return jsonObject;
    }


}
