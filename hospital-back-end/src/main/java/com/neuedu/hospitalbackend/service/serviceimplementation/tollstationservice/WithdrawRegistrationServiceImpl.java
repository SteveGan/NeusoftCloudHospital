package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.po.*;
import com.neuedu.hospitalbackend.model.vo.RegistrationParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.PatientService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.WithdrawRegistrationService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ConstantMap;
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
        JSONObject jsonObject = new JSONObject();
        Registration registration = registrationMapper.getRegistrationInfo(registrationId);
        String convert = ConstantMap.convert("看诊时间段", (byte)1);
        if (registration != null)
            return CommonResult.success(registration);
        else
            return CommonResult.fail(E_703);
    }

    @Override
    @Transactional
    public CommonResult operateTransactionLog(RegistrationParam registrationParam){
        JSONObject jsonObject = new JSONObject();
        String invoiceCode = null; //冲正发票号
        //判断患者挂号状态是否是待诊状态
        if (registrationParam.getPatientCaseStatus() == 1){

           //得到原缴费记录
            TransactionLog originalTransactionLog = transactionLogMapper.getLogByRegistrationIdAndType(registrationParam.getRegistrationId(), "挂号费");
            synchronized (this) {

                //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
                CommonResult result = invoiceService.getNextInvoiceCode();
                invoiceCode = (String) result.getData();

                System.out.println("冲正发票号1" + invoiceCode);
               /* String invoice_code = invoiceMapper.getAvailableInvoiceCode();
                count += invoiceMapper.updateInvoiceStatusById(invoice_code);*/

                //向缴费表中添加新的缴费记录  --冲正
                CommonResult insertResult = transactionService.insertTransactionLog(
                        invoiceCode, originalTransactionLog.getRegistrationId(),
                        originalTransactionLog.getPatientId(), originalTransactionLog.getRoleId(),
                        originalTransactionLog.getType(), null, null, null,
                        originalTransactionLog.getAmount(), originalTransactionLog.getPayType(),
                        originalTransactionLog.getTotalMoney().negate(), (byte)4);
                if (insertResult.getCode() == 500)
                    return insertResult;
            }

            int count = 0;
            //将原有缴费记录状态更改为已退费 --已退费
            originalTransactionLog.setStatus((byte)3);
            count = transactionLogMapper.updateSelective(originalTransactionLog);
            jsonObject.put("更改原有缴费记录", count);

            //向异常表中添加新的记录
            System.out.println("冲正发票号2" + invoiceCode);
            CommonResult insertExceptionResult = transactionService.insertTransactionExceptionLog(
                    originalTransactionLog.getInvoiceCode(),null, invoiceCode,
                    originalTransactionLog.getRoleId(), "挂号退费" );
            if (insertExceptionResult.getCode() == 500)
                return insertExceptionResult;

            //在挂号表中更新该病历号的状态 --已退号
            count = registrationMapper.updateStatusById(registrationParam.getRegistrationId());
            jsonObject.put("更改挂号状态", count);

            //从门诊病历首页移除该病历号，删除医生端的病历记录
            count = patientCaseMapper.deletePatientCaseById(registrationParam.getRegistrationId());
            jsonObject.put("删除病历记录", count);

            //增加该医生的剩余号额
            count = arrangementMapper.updateRemainingAppointment(registrationParam.getAppointmentDateStr(), registrationParam.getTimeSlot(),
                    registrationParam.getRoleId(), registrationParam.getRegistrationLevelId(), 1, registrationParam.getDepartmentId());
            jsonObject.put("修改医生剩余号额", count);
            return CommonResult.success(jsonObject);
        }
        else
            return CommonResult.fail(E_701);
    }
}
