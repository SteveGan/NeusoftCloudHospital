package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.constant.Cache;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.model.po.*;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.RegistrationService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_700;

/**
 * @author Polaris
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {


    @Resource
    private ArrangementMapper arrangementMapper;
    @Resource
    private RegistrationLevelMapper registrationLevelMapper;
    @Resource
    private TransactionLogMapper transactionLogMapper;
    @Resource
    private RegistrationMapper registrationMapper;
    @Resource
    private PatientMapper patientMapper;
    @Resource
    private PatientCaseMapper patientCaseMapper;
    @Resource
    private TransactionService transactionService;
    @Resource
    private InvoiceService invoiceService;

    @Override
    public synchronized CommonResult getNextRegistrationId() {
        Integer nextId = Cache.getNextRegistrationId();
        System.out.println("[INFO]正在使用病历号: " + nextId);
        Cache.setNextRegistrationId(nextId + 1);
        return CommonResult.success(nextId);
    }

    @Override
    public CommonResult listAvailableDoctors(RegistrationParam registrationParam){
        String appointmentDateStr = registrationParam.getAppointmentDateStr();
        Byte timeSlot = registrationParam.getTimeSlot();
        Short registrationLevelId = registrationParam.getRegistrationLevelId();
        Integer departmentId = registrationParam.getDepartmentId();
        List<Arrangement> availableDoctors = arrangementMapper.listAvailableDoctors(appointmentDateStr, timeSlot, registrationLevelId, departmentId);
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
        boolean isBuyCaseBook = registrationParam.getIsBuyCaseBook();
        double cost = registrationLevelMapper.getRegistrationLevelCostById(registrationLevelId).doubleValue();
        double bookCost = 1;
        double totalCost;
        if (isBuyCaseBook){
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
        String invoiceCode = registrationParam.getInvoiceCode();
        Integer registrationId = registrationParam.getRegistrationId();
        Integer cashierId = registrationParam.getCashierId();
        Byte payType = registrationParam.getPayType();
        BigDecimal totalMoney = registrationParam.getTotalFee();
        String appointmentDateStr = registrationParam.getAppointmentDateStr();
        Byte timeSlot = registrationParam.getTimeSlot();
        Integer doctorId = registrationParam.getRoleId();
        Short registrationLevelId = registrationParam.getRegistrationLevelId();
        Integer departmentId = registrationParam.getDepartmentId();
        boolean isBuyCaseBook = registrationParam.getIsBuyCaseBook();

        String idCard = registrationParam.getIdCard();
        String address = registrationParam.getAddress();
        String name = registrationParam.getName();
        Byte gender = registrationParam.getGender();
        String birthdayStr = registrationParam.getBirthdayStr();

        JSONObject jsonObject = new JSONObject();

        //向缴费表中添加新的缴费记录  --已缴费
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setInvoiceCode(invoiceCode);
        transactionLog.setRegistrationId(registrationId);
        transactionLog.setRoleId(doctorId);
        transactionLog.setCashierId(cashierId);
        transactionLog.setType("挂号费");
        transactionLog.setAmount((short)(1));
        transactionLog.setPayType(payType);
        transactionLog.setTotalMoney(totalMoney);
        transactionLog.setStatus((byte)2);
        CommonResult insertResult = transactionService.insertTransactionLog(transactionLog);
        if (insertResult.getCode() == 500)
            return insertResult;

        invoiceService.updateStatus((byte)3, registrationParam.getInvoiceCode());

        //向挂号表中添加新的挂号记录 --默认正常
        Registration registration = new Registration();
        registration.setId(registrationId);
        registration.setAppointmentDate(Date.valueOf(appointmentDateStr));
        registration.setTimeSlot(timeSlot);
        registration.setRoleId(doctorId);
        registration.setRegistrationLevelId(registrationLevelId);
        registration.setDepartmentId(departmentId);
        registration.setTotalFee(totalMoney);
        registration.setCashierId(cashierId);
        registration.setPayType(payType);
        registration.setBuyCaseBook(isBuyCaseBook);
        int count1 = registrationMapper.insertSelective(registration);
        jsonObject.put("insertRegistrationLog", count1);

        if (count1 > 0) {
            //更新 所选医生 对应的余号数量
            int count2 = arrangementMapper.updateRemainingAppointment(appointmentDateStr, timeSlot, doctorId, registrationLevelId, -1, departmentId);
            jsonObject.put("updateRemainingAppointment", count2);

            if (count2 > 0) {
                //检查患者是否已在本系统中
                int count3;
                Integer patientId = patientMapper.getPatientByIdCard(idCard);
                System.out.println("patientId" + patientId);
                if (patientId == null) {
                    Patient patient = new Patient();
                    patient.setIdCard(idCard);
                    patient.setAddress(address);
                    patient.setGender(gender);
                    patient.setName(name);
                    patient.setBirthday(Date.valueOf(birthdayStr));
                    count3 = patientMapper.insertSelective(patient);
                    jsonObject.put("insertPatient", count3);
                    patientId = patient.getId();
                }
                else{
                    Patient patient = new Patient();
                    patient.setId(patientId);
                    patient.setAddress(address);
                    patient.setName(name);
                    count3 = patientMapper.updatePatientInfo(patient);
                    jsonObject.put("getPatient", count3);
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
                patientCase.setRegistrationId(registrationId);
                patientCase.setPatientId(patientId);
                patientCase.setPatientName(name);
                patientCase.setRoleId(doctorId);
                patientCase.setAppointmentDate(Date.valueOf(appointmentDateStr));
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
        List<Registration> registrations = new ArrayList<>();
        registrations.addAll(registrationMapper.listAllNormalRegistrationsInfo());
        registrations.addAll(registrationMapper.listAllRefundedRegistrationInfo());
        return CommonResult.success(registrations);
    }

}
