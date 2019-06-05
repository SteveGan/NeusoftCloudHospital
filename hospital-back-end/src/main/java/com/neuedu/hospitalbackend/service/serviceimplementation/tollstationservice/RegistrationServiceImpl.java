package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.constant.Cache;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.model.po.*;
import com.neuedu.hospitalbackend.service.serviceimplementation.commonservice.TransactionServiceImpl;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_700;

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
    private InvoiceMapper invoiceMapper;

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
    public synchronized CommonResult getNextInvoiceCode() {
        String nextInvoiceCode = Cache.getNextInvoiceCode();
        System.out.println("[INFO]正在使用: " + nextInvoiceCode);
        return CommonResult.success(nextInvoiceCode);
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
        BigDecimal cost = registrationLevelMapper.getRegistrationLevelCostById(registrationLevelId);
        BigDecimal bookCost = new BigDecimal(1);
        BigDecimal totalCost;
        if (registrationParam.getBuyCaseBook() == true)
            totalCost = cost.add(bookCost);
        else
            totalCost = cost;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalFee", totalCost);
        return CommonResult.success(jsonObject);
    }

    @Override
    @Transactional
    public CommonResult makeRegistration(RegistrationParam registrationParam){
        JSONObject jsonObject = new JSONObject();

        //向缴费表中添加新的缴费记录  --已缴费
        CommonResult insertResult = transactionService.insertTransactionLog(
                registrationParam.getInvoiceCode(), registrationParam.getRegistrationId(),
                registrationParam.getPatientId(), registrationParam.getCashierId(),
                "挂号费", null, null, null,
                (short)(1), registrationParam.getPayType(), registrationParam.getTotalFee(), (byte)2);
        if (insertResult.getCode() == 500)
            return insertResult;
        TransactionLog transactionLog = (TransactionLog) insertResult.getData();

        /*transactionLog.setInvoiceCode(registrationParam.getInvoiceCode());
        transactionLog.setRegistrationId(registrationParam.getRegistrationId());
        transactionLog.setRoleId(registrationParam.getCashierId());
        transactionLog.setType("挂号费");
        transactionLog.setAmount((short)(1));
        transactionLog.setPayType(registrationParam.getPayType());
        transactionLog.setTotalMoney(registrationParam.getTotalFee());
        transactionLog.setStatus((byte)2);
        count += transactionLogMapper.insertSelective(transactionLog);*/

        int count = 0;
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
        registration.setBuyCaseBook(registrationParam.getBuyCaseBook());

        count = registrationMapper.insertSelective(registration);
        jsonObject.put("insertRegistrationLog", count);

        if (count > 0) {
            //更新 所选医生 对应的余号数量
            count = arrangementMapper.updateRemainingAppointment(registrationParam.getAppointmentDateStr(), registrationParam.getTimeSlot(), registrationParam.getRoleId(), registrationParam.getRegistrationLevelId(), -1);
            jsonObject.put("updateRemainingAppointment", count);

            if (count > 0) {
                //检查患者是否已在本系统中
                Integer patientId = patientMapper.getPatientByIdCard(registrationParam.getIdCard());
                if (patientId == null) {
                    Patient patient = new Patient();
                    patient.setIdCard(registrationParam.getIdCard());
                    patient.setAddress(registrationParam.getAddress());
                    patient.setGender(registrationParam.getGender());
                    patient.setName(registrationParam.getName());
                    patient.setBirthday(Date.valueOf(registrationParam.getBirthdayStr()));
                    count = patientMapper.insertSelective(patient);
                    jsonObject.put("insertPatient", count);
                    patientId = patient.getId();
                }

                transactionLog.setPatientId(patientId);
                registration.setPatientId(patientId);
                count = transactionLogMapper.updateSelective(transactionLog);
                if (count == 0)
                    return CommonResult.fail(E_700);
                jsonObject.put("updateTransactionLog", count);
                count = registrationMapper.updateSelective(registration);
                if (count == 0)
                    return CommonResult.fail(E_700);
                jsonObject.put("updateRegistration", count);

                //向病历表中添加新的病历记录 --默认待诊
                PatientCase patientCase = new PatientCase();
                patientCase.setRegistrationId(registrationParam.getRegistrationId());
                patientCase.setPatientId(patientId);
                patientCase.setPatientName(registrationParam.getName());
                patientCase.setRoleId(registrationParam.getRoleId());
                count = patientCaseMapper.insertSelective(patientCase);
                jsonObject.put("insertPatientCase", count);
            }
            return CommonResult.success(jsonObject);
        }
        return CommonResult.fail();
    }
}
