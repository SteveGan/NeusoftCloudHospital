package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.po.*;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.PatientService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.WithdrawRegistrationService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neuedu.hospitalbackend.util.ResultCode.*;

@Service
public class WithdrawRegistrationServiceImpl implements WithdrawRegistrationService {

    @Autowired
    private PatientService patientService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private RegistrationMapper registrationMapper;
    @Autowired
    private TransactionLogMapper transactionLogMapper;
    @Autowired
    private PatientCaseMapper patientCaseMapper;
    @Autowired
    private ArrangementMapper arrangementMapper;

    @Override
    public CommonResult getRegistrationInfo(Integer registrationId){
        Registration registration = registrationMapper.getRegistrationInfo(registrationId);
        if (registration != null)
            return CommonResult.success(registration);
        else
            return CommonResult.fail();
    }

    @Override
    @Transactional
    public CommonResult operateTransactionLog(RegistrationParam registrationParam, Byte patientCaseStatus){
        int count = 0;
        //判断患者挂号状态是否是待诊状态
        if (patientCaseStatus == 1){

           //得到原缴费记录
            TransactionLog originalTransactionLog = transactionLogMapper.getLogByRegistrationIdAndType(registrationParam.getRegistrationId(), "挂号费");
            TransactionLog newTransactionLog = new TransactionLog();
            synchronized (this) {

                //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
                CommonResult result = invoiceService.getNextInvoiceCode();
                String invoice_code = (String) result.getData();

               /* String invoice_code = invoiceMapper.getAvailableInvoiceCode();
                count += invoiceMapper.updateInvoiceStatusById(invoice_code);*/

                //向缴费表中添加新的缴费记录  --冲正
                CommonResult insertResult = transactionService.insertTransactionLog(
                        invoice_code, originalTransactionLog.getRegistrationId(),
                        originalTransactionLog.getPatientId(), originalTransactionLog.getRoleId(),
                        originalTransactionLog.getType(), null, null, null,
                        originalTransactionLog.getAmount(), originalTransactionLog.getPayType(),
                        originalTransactionLog.getTotalMoney().negate(), (byte)4);
                if (insertResult.getCode() == 500)
                    return insertResult;
            }

            //将原有缴费记录状态更改为已退费 --已退费
            originalTransactionLog.setStatus((byte)3);
            count += transactionLogMapper.updateSelective(originalTransactionLog);

            //向异常表中添加新的记录
            CommonResult insertExceptionResult = transactionService.insertTransactionExceptionLog(
                    originalTransactionLog.getInvoiceCode(),null, newTransactionLog.getInvoiceCode(),
                    registrationParam.getCashierId(), "挂号退费" );
            if (insertExceptionResult.getCode() == 500)
                return insertExceptionResult;

            //在挂号表中更新该病历号的状态 --已退号
            count += registrationMapper.updateStatusById((byte)0, registrationParam.getRegistrationId());

            //从门诊病历首页移除该病历号，删除医生端的病历记录
            count += patientCaseMapper.deletePatientCaseById(registrationParam.getRegistrationId());

            //增加该医生的剩余号额
            count += arrangementMapper.updateRemainingAppointment(registrationParam.getAppointmentDateStr(), registrationParam.getTimeSlot(),
                    registrationParam.getRoleId(), registrationParam.getRegistrationLevelId(), 1);
            return CommonResult.success(count);
        }
        else
            return CommonResult.fail(E_701);
    }
}
