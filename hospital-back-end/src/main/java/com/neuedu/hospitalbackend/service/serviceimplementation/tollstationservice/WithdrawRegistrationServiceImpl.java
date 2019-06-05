package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.po.*;
import com.neuedu.hospitalbackend.model.vo.DoctorParam;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceimplementation.commonservice.TransactionServiceImpl;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.PatientService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.WithdrawRegistrationService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawRegistrationServiceImpl implements WithdrawRegistrationService {

    @Autowired
    private PatientService patientService;
    @Autowired
    private TransactionService transactionService = new TransactionServiceImpl();
    @Autowired
    private RegistrationMapper registrationMapper;
    @Autowired
    private TransactionLogMapper transactionLogMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private TransactionExceptionLogMapper transactionExceptionLogMapper;

    public CommonResult getRegistrationInfo(Integer registrationId){
        Registration registration = registrationMapper.getRegistrationInfo(registrationId);
        if (registration != null)
            return CommonResult.success(registration);
        else
            return CommonResult.fail();
    }

    public CommonResult operateOriginalTransactionLog(RegistrationParam registrationParam, Byte patientCaseStatus){
        int count = 0;
        //判断患者挂号状态是否是待诊状态
        if (patientCaseStatus == 1){
           //得到原缴费记录
            //transactionService.getTransactionLogSelective(registrationParam.getRegistrationId())
            TransactionLog originalTransactionLog = transactionLogMapper.getLogByRegistrationIdAndType(registrationParam.getRegistrationId(), "挂号费");
            TransactionLog newTransactionLog = new TransactionLog();
            synchronized (this) {
                //通过查询invoice表得到新的缴费记录的发票号并将其状态改为暂用
                String invoice_code = invoiceMapper.getAvailableInvoiceCode();
                count += invoiceMapper.updateInvoiceStatusById(invoice_code);
                //向缴费表中添加新的缴费记录  --冲正，并且返回该记录的发票号
                newTransactionLog.setInvoiceCode(invoice_code);
                newTransactionLog.setRegistrationId(originalTransactionLog.getPatientId());
                newTransactionLog.setPatientId(originalTransactionLog.getPatientId());
                newTransactionLog.setRoleId(originalTransactionLog.getRoleId());
                newTransactionLog.setType(originalTransactionLog.getType());
                newTransactionLog.setAmount(originalTransactionLog.getAmount());
                newTransactionLog.setTotalMoney(originalTransactionLog.getTotalMoney().negate());
                newTransactionLog.setStatus((byte)4);
                count += transactionLogMapper.insertSelective(newTransactionLog);
            }
            //将原有缴费记录状态更改为已退费 --已退费
            originalTransactionLog.setStatus((byte)3);
            count += transactionLogMapper.updateSelective(originalTransactionLog);
            //向异常表中添加新的记录
            TransactionExceptionLog transactionExceptionLog = new TransactionExceptionLog();
            transactionExceptionLog.setOriginalInvoiceCode(originalTransactionLog.getInvoiceCode());
            transactionExceptionLog.setReverseInvoiceCode(newTransactionLog.getInvoiceCode());
            transactionExceptionLog.setRoleId(registrationParam.getCashierId());
            transactionExceptionLog.setReason("挂号退费");
            count += transactionExceptionLogMapper.insertSelective(transactionExceptionLog);
            //在挂号表中更新该病历号的状态 --已退号
            count += registrationMapper.updateStatusById((byte)0, registrationParam.getRegistrationId());
            //从门诊病历首页移除该病历号，删除医生端的病历记录
            return null;
        }
        else
            return CommonResult.fail();
    }

    public CommonResult updateRemainingAppointment(DoctorParam doctorParam){
        return null;
    }
    public CommonResult deleteCaseLog(PatientCase patientCase){
        return null;
    }
}
