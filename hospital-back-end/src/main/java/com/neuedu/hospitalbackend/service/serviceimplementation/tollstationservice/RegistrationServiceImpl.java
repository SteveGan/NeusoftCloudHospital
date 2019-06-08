package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.constant.Cache;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.model.po.*;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ConstantMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_700;
import static com.neuedu.hospitalbackend.util.ResultCode.E_701;
import static com.neuedu.hospitalbackend.util.ResultCode.E_703;

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
    private PatientCaseMapper patientCaseMapper;
    @Autowired
    private TransactionService transactionService;

    @Override
    public synchronized CommonResult getNextRegistrationId() {
        Integer nextId = Cache.getNextRegistrationId();
        System.out.println("[INFO]正在使用: " + nextId);
        Cache.setNextRegistrationId(nextId + 1);
        return CommonResult.success(nextId);
    }

    @Override
    public CommonResult listAvailableDoctors(RegistrationParam registrationParam){
        List<Arrangement> availableDoctors = arrangementMapper.listAvailableDoctors(registrationParam.getAppointmentDateStr(), registrationParam.getTimeSlot(), registrationParam.getRegistrationLevelId(), registrationParam.getDepartmentId());
        /*for(Arrangement a: availableDoctors){
            System.out.println(a.getUserName());
        }*/
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("availableDoctors", availableDoctors);
        return CommonResult.success(jsonObject);
    }

    @Override
    public CommonResult calculateTotalFee(RegistrationParam registrationParam){
        //根据看诊医生和挂号级别，是否需要病历本，算出应收金额
        Short registrationLevelId = registrationParam.getRegistrationLevelId();
        double cost = registrationLevelMapper.getRegistrationLevelCostById(registrationLevelId).doubleValue();
        double bookCost = 1;
        double totalCost = 0;
        if (registrationParam.getIsBuyCaseBook() == true){
            totalCost = cost + bookCost;
        }
        else
            totalCost = cost;
        System.out.println("after" + totalCost);
        return CommonResult.success(totalCost);
    }

    @Override
    @Transactional
    public CommonResult makeRegistration(RegistrationParam registrationParam){
        JSONObject jsonObject = new JSONObject();

        //向缴费表中添加新的缴费记录  --已缴费
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setInvoiceCode(registrationParam.getInvoiceCode());
        transactionLog.setRegistrationId(registrationParam.getRegistrationId());
        transactionLog.setRoleId(registrationParam.getCashierId());
        transactionLog.setType("挂号费");
        transactionLog.setAmount((short)(1));
        transactionLog.setPayType(registrationParam.getPayType());
        transactionLog.setTotalMoney(registrationParam.getTotalFee());
        transactionLog.setStatus((byte)2);
        CommonResult insertResult = transactionService.insertTransactionLog(transactionLog);
        if (insertResult.getCode() == 500)
            return insertResult;

        //向挂号表中添加新的挂号记录 --默认正常
        Registration registration = new Registration();
        registration.setId(registrationParam.getRegistrationId());
        registration.setAppointmentDate(Date.valueOf(registrationParam.getAppointmentDateStr()));
        registration.setTimeSlot(registrationParam.getTimeSlot());
        registration.setRoleId(registrationParam.getRoleId());
        registration.setRegistrationLevelId(registrationParam.getRegistrationLevelId());
        registration.setDepartmentId(registrationParam.getDepartmentId());
        registration.setTotalFee(registrationParam.getTotalFee());
        registration.setCashierId(registrationParam.getCashierId());
        registration.setPayType(registrationParam.getPayType());
        registration.setBuyCaseBook(registrationParam.getIsBuyCaseBook());
        int count1 = registrationMapper.insertSelective(registration);
        jsonObject.put("insertRegistrationLog", count1);

        if (count1 > 0) {
            //更新 所选医生 对应的余号数量
            int count2 = arrangementMapper.updateRemainingAppointment(registrationParam.getAppointmentDateStr(), registrationParam.getTimeSlot(), registrationParam.getRoleId(), registrationParam.getRegistrationLevelId(), -1, registrationParam.getDepartmentId());
            jsonObject.put("updateRemainingAppointment", count2);

            if (count2 > 0) {
                //检查患者是否已在本系统中
                int count3 = 0;
                Integer patientId = patientMapper.getPatientByIdCard(registrationParam.getIdCard());
                if (patientId == null) {
                    Patient patient = new Patient();
                    patient.setIdCard(registrationParam.getIdCard());
                    patient.setAddress(registrationParam.getAddress());
                    patient.setGender(registrationParam.getGender());
                    patient.setName(registrationParam.getName());
                    patient.setBirthday(Date.valueOf(registrationParam.getBirthdayStr()));
                    count3 = patientMapper.insertSelective(patient);
                    jsonObject.put("insertPatient", count3);
                    patientId = patient.getId();
                }
                else{
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    patient.setAddress(registrationParam.getAddress());
                    patient.setName(registrationParam.getName());
                    patientMapper.updatePatientInfo(patient);
                }

                transactionLog.setPatientId(patientId);
                int count4 = transactionLogMapper.update(transactionLog);
                jsonObject.put("updateTransactionLog", count4);
                if (count4 == 0)
                    return CommonResult.fail(E_700);

                registration.setPatientId(patientId);
                int count5 = registrationMapper.updateSelective(registration);
                jsonObject.put("updateRegistration", count5);
                if (count5 == 0)
                    return CommonResult.fail(E_700);


                //向病历表中添加新的病历记录 --默认待诊
                PatientCase patientCase = new PatientCase();
                patientCase.setRegistrationId(registrationParam.getRegistrationId());
                patientCase.setPatientId(patientId);
                patientCase.setPatientName(registrationParam.getName());
                patientCase.setRoleId(registrationParam.getRoleId());
                int count6 = patientCaseMapper.insertSelective(patientCase);
                jsonObject.put("insertPatientCase", count6);
                if ((count1 + count2 + count3 + count4 + count5 + count6) != 6)
                    return CommonResult.fail();
                else
                    return CommonResult.success(jsonObject);
            }
        }
        return CommonResult.fail();
    }

    @Override
    public CommonResult listRegistrations(){
        JSONObject jsonObject = new JSONObject();
        List<Registration> normalRegistrations = registrationMapper.listAllNormalRegistrationsInfo();
        List<Registration> refundedRegistrations = registrationMapper.listAllRefundedRegistrationInfo();
        jsonObject.put("normalRegistrations", normalRegistrations);
        jsonObject.put("refundedRegistrations", refundedRegistrations);
        return CommonResult.success(jsonObject);
    }

}
