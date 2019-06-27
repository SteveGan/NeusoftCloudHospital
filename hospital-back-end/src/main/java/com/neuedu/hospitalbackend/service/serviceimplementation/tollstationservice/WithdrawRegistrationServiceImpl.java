package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.po.*;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.WithdrawRegistrationService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.neuedu.hospitalbackend.util.ResultCode.*;

/**
 * @author Polaris
 */
@Service
public class WithdrawRegistrationServiceImpl implements WithdrawRegistrationService {

    @Resource
    private TransactionService transactionService;
    @Resource
    private InvoiceService invoiceService;
    @Resource
    private RegistrationMapper registrationMapper;
    @Resource
    private TransactionLogMapper transactionLogMapper;
    @Resource
    private PatientCaseMapper patientCaseMapper;
    @Resource
    private ArrangementMapper arrangementMapper;

    @Override
    @Transactional
    public CommonResult operateTransactionLog(RegistrationParam registrationParam){
        Integer registrationId = registrationParam.getRegistrationId();
        Integer newCashierId = registrationParam.getNewCashierId();
        String appointmentDateStr = registrationParam.getAppointmentDateStr();
        Byte timeSlot = registrationParam.getTimeSlot();
        Integer doctorId = registrationParam.getRoleId();
        Short registrationLevelId = registrationParam.getRegistrationLevelId();
        Integer departmentId = registrationParam.getDepartmentId();

        String invoiceCode; //冲正发票号
        //判断患者挂号状态是否是待诊状态
        if (registrationParam.getPatientCaseStatus() == 1){

            int count = 0;

            //得到原缴费记录
            TransactionLog originalTransactionLog = transactionLogMapper.getLogByRegistrationIdAndType(registrationId, "挂号费");
            if (originalTransactionLog == null)
                return CommonResult.fail();
            String originalInvoiceCode = originalTransactionLog.getInvoiceCode();

            //将原有缴费记录状态更改为已退费 --已退费
            originalTransactionLog.setStatus((byte)3);
            count += transactionLogMapper.update(originalTransactionLog);

            synchronized (this) {
                //通过查询invoice表得到新的缴费记录的发票号并将其状态改为暂用
                CommonResult result = invoiceService.getNextInvoiceCode();
                invoiceCode = (String) result.getData();
            }

            //向缴费表中添加新的缴费记录  --冲正
            TransactionLog newTransactionLog = (TransactionLog) originalTransactionLog.clone();
            newTransactionLog.setId(null);
            newTransactionLog.setStatus((byte)4);
            newTransactionLog.setCashierId(newCashierId);
            newTransactionLog.setInvoiceCode(invoiceCode);
            newTransactionLog.setTotalMoney(newTransactionLog.getTotalMoney().negate());
            CommonResult insertResult = transactionService.insertTransactionLog(newTransactionLog);
            if (insertResult.getCode() == 500)
                return insertResult;
            invoiceService.updateStatus((byte)3, invoiceCode);

            //向异常表中添加新的记录
            CommonResult insertExceptionResult = transactionService.insertTransactionExceptionLog(
                    originalInvoiceCode,null, invoiceCode, newCashierId, "挂号退费" );
            if (insertExceptionResult.getCode() == 500)
                return insertExceptionResult;

            //在挂号表中更新该病历号的状态 --已退号
            count += registrationMapper.updateStatusById(registrationId);

            //从门诊病历首页移除该病历号，删除医生端的病历记录
            count += patientCaseMapper.deletePatientCaseById(registrationId);

            //增加该医生的剩余号额
            count += arrangementMapper.updateRemainingAppointment(appointmentDateStr, timeSlot, doctorId, registrationLevelId, 1, departmentId);
            if (count != 4)
                return CommonResult.fail();
            else
                return CommonResult.success(invoiceCode);
        }
        else
            return CommonResult.fail(E_701);
    }
}
