package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.model.vo.TransactionParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.PaymentService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.neuedu.hospitalbackend.util.ResultCode.E_700;
import static com.neuedu.hospitalbackend.util.ResultCode.E_704;
import static com.neuedu.hospitalbackend.util.ResultCode.E_712;

/**
 * @author Polaris
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private TransactionLogMapper transactionLogMapper;
    @Resource
    private ExaminationMapper examinationMapper;
    @Resource
    private InspectionMapper inspectionMapper;
    @Resource
    private TreatmentMapper treatmentMapper;
    @Resource
    private RecipeMapper recipeMapper;
    @Resource
    private MedicineMapper medicineMapper;
    @Resource
    private InvoiceService invoiceService;
    @Resource
    private TransactionService transactionService;

    @Override
    public CommonResult listTransactionLogsByRegistrationId(Integer registrationId) {
        //列出指定用户的所有发票号、缴费状态（已缴费、已退费、冲正、作废、冻结）
        List<HashMap> invoiceCollection = transactionLogMapper.listInvoiceCodeAndStatusByRegistrationId(registrationId);
        //列出指定用户的待缴费、可退费状态缴费记录
        List<HashMap> logs = new ArrayList<>();
        //logs.add(transactionLogMapper.getRegistrationLog(registrationId));
        logs.addAll(transactionLogMapper.listLogsByTableName("inspection", registrationId));
        logs.addAll(transactionLogMapper.listLogsByTableName("examination", registrationId));
        logs.addAll(transactionLogMapper.listLogsByTableName("treatment", registrationId));
        logs.addAll(transactionLogMapper.listRecipeLogs(registrationId));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("invoiceCollection", invoiceCollection);
        jsonObject.put("transactionLogs", logs);
        return CommonResult.success(jsonObject);
    }

    @Override
    public CommonResult listTransactionLogsByInvoiceCode(String invoiceCode){
        List<TransactionLog> transactionLogs = transactionLogMapper.listLogsByInvoiceCode(invoiceCode);
        return CommonResult.success(transactionLogs);
    }

    @Override
    public CommonResult listTransactionLogsByCollectionAndProjectId(Integer collectionId, Integer projectId){
        List<TransactionLog> transactionLogs = transactionLogMapper.listItemsByCollectionIdAndProjectId(collectionId, projectId);
        return CommonResult.success(transactionLogs);
    }

    @Override
    @Transactional
    public CommonResult updateTransactionLogsAsPaid(List<TransactionParam> transactionParams) {

        //未缴费中未缴费的项目分配发票号
        String newInvoiceCode;
        synchronized (this) {
            //通过查询invoice表得到新的缴费记录的发票号并将其状态改为暂用
            CommonResult result = invoiceService.getNextInvoiceCode();
            newInvoiceCode = (String) result.getData();
        }

        int count = 0;
        for (TransactionParam transactionParam : transactionParams) {
            if(transactionParam.getStatus() == 1){
                Integer collectionId = transactionParam.getCollectionId();
                Integer projectId = transactionParam.getProjectId();
                List<TransactionLog> detailedLogs = transactionLogMapper.listItemsByCollectionIdAndProjectId(collectionId, projectId);
                for(TransactionLog t: detailedLogs){
                    t.setInvoiceCode(newInvoiceCode);
                    t.setRoleId(transactionParam.getRoleId());
                    t.setStatus((byte)2);
                    count += transactionLogMapper.update(t);
                }
            }
        }
        if (count > 0){
            invoiceService.updateStatus((byte)3, newInvoiceCode);
            return CommonResult.success(count);
        }

        return CommonResult.fail(E_700);
    }

    @Override
    public CommonResult updateTransactionLogsAsReturned(List<TransactionParam> transactionLogParams) {
        Integer newCashierId = null;
        //将前端传进来的所有待退费记录转换成<invoiceCode, List<TransactionParam>>的形式
        HashMap<String, List<TransactionParam>> collection = new HashMap<>();
        for (TransactionParam t : transactionLogParams) {
            String invoiceCode = t.getInvoiceCode();
            if (!collection.containsKey(invoiceCode)) {
                collection.put(invoiceCode, new ArrayList<>());
                collection.get(invoiceCode).add(t);
            } else
                collection.get(invoiceCode).add(t);
        }

        //执行退费系列操作
        for(String invoiceCode: collection.keySet()){
            // 同一个发票号下退费的项目集合
            List<TransactionParam> returnedProjectList = collection.get(invoiceCode);
            //退费项目的id集合
            List<Integer> returnedIdList = new ArrayList<>();

            for(TransactionParam returnedProject: returnedProjectList){
                Integer returnedCollectionId = returnedProject.getCollectionId();
                newCashierId = returnedProject.getNewCashierId();
                Integer returnedProjectId = returnedProject.getProjectId();
                Byte returnedTransactionStatus = returnedProject.getStatus(); //缴费记录状态(已缴费、已退费...)
                Byte returnedProjectStatus = returnedProject.getProjectStatus(); //项目自身状态(开立、已登记...)
                returnedIdList.addAll(transactionLogMapper.listIdByCollectionAndProjectId(invoiceCode, returnedCollectionId, returnedProjectId));
                System.out.println(returnedIdList.get(0));
                // 判断项目的类别（检查、检验、处置、药方）
                Byte itemCategory;
                if(returnedCollectionId.toString().startsWith("2"))
                    itemCategory = 1;
                else if(returnedCollectionId.toString().startsWith("3"))
                    itemCategory = 2;
                else if(returnedCollectionId.toString().startsWith("4"))
                    itemCategory = 3;
                else if(returnedCollectionId.toString().startsWith("5"))
                    itemCategory = 4;
                else
                    return CommonResult.fail(E_712);

                // 先判断returnedProjectList中的项目是否都为可退状态
                //医技项目可退费条件：已缴费 + 开立
                if(itemCategory != 4){
                    if(!(returnedTransactionStatus == 2 && returnedProjectStatus == 2))
                        return CommonResult.fail(E_704);
                }
                //药方可退费条件：已缴费 + 已退药 / 已缴费 + 开立且退药数量小于可退药数量
                else {
                    if(returnedTransactionStatus == 2){
                        if(!((returnedProjectStatus == 2 && returnedProject.getReturnAmount() < returnedProject.getRemainAmount())
                                || (returnedProjectStatus == 5)))
                            return CommonResult.fail(E_704);
                    }
                }

                // 在检查/检验/处置表中更改想要退费的项目状态 --已作废
                switch (itemCategory){
                    case (byte)1:
                        inspectionMapper.updateStatus(returnedCollectionId, returnedProjectId, (byte) 3);
                        break;
                    case (byte)2:
                        examinationMapper.updateStatus(returnedCollectionId, returnedProjectId, (byte) 3);
                        break;
                    case (byte)3:
                        treatmentMapper.updateStatus(returnedCollectionId, returnedProjectId, (byte) 3);
                        break;
                    case (byte)4:
                        //如果药品本身是已退药的状态,则无需更改对应药品状态和数量
                        //如果药品本身是开立（未取药）的状态，则需更改对应处方中药品的remain amount
                        Short returnedReturnAmount = returnedProject.getReturnAmount();
                        Short returnedRemainAmount = returnedProject.getRemainAmount();
                        if(returnedProjectStatus == 2){
                            recipeMapper.updateRemainAmount(returnedCollectionId, returnedProjectId, returnedReturnAmount);
                            //如果退药数量 = 药品可退数量，则该药品状态应改为作废
                            if(returnedReturnAmount.equals(returnedRemainAmount))
                                recipeMapper.updateStatus(returnedCollectionId, returnedProjectId, (byte) 3);
                        }
                        break;
                }
            }

            // 更改 退费的项目所在同一发票号的 相关缴费记录状态--已退费
            transactionLogMapper.updateSelectiveAsReturned(invoiceCode);
            // 得到 退费的项目所在同一发票号 缴费记录
            List<TransactionLog> transactionLogs = transactionLogMapper.listLogsByInvoiceCode(invoiceCode);

            //向缴费表中添加 退费的项目所在第一层级的 冲正记录（即检验单B中的所有项目） --冲正，
            String reverseInvoiceCode;
            synchronized (this) {
                //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
                CommonResult result = invoiceService.getNextInvoiceCode();
                reverseInvoiceCode = (String) result.getData();
            }

            for(TransactionLog transactionLog: transactionLogs){
                //向缴费表中添加新的缴费记录  --冲正
                TransactionLog reverseTransactionLog = (TransactionLog) transactionLog.clone();
                reverseTransactionLog.setId(null);
                reverseTransactionLog.setRoleId(newCashierId);
                reverseTransactionLog.setInvoiceCode(reverseInvoiceCode);
                reverseTransactionLog.setStatus((byte)4);
                reverseTransactionLog.setTotalMoney(transactionLog.getTotalMoney().negate());
                CommonResult insertReverseResult = transactionService.insertTransactionLog(reverseTransactionLog);
                if (insertReverseResult.getCode() == 500)
                    return insertReverseResult;
            }
            invoiceService.updateStatus((byte)3, reverseInvoiceCode);

            // 如果某个发票下的所有项目没有都被退费，则产生新的缴费记录
            // 对于医技项目来说，没有都被退费： transactionLogs.size() != returnedProjectList.size()
            //对于药品来说，没有都被退费：某个退费的项目returnAmount != amount / transactionLogs.size() != returnedProjectList.size()
            boolean isRecipeAllReturn = true;
            for(TransactionParam project: returnedProjectList){
                if(project.getRemainAmount() > 0)
                    isRecipeAllReturn = false;
            }

            String newInvoiceCode = null;
            if(transactionLogs.size() != returnedProjectList.size() || !isRecipeAllReturn) {
                synchronized (this) {

                    //通过查询invoice表得到新的缴费记录的发票号并将其状态改为暂
                    // 用
                    CommonResult result = invoiceService.getNextInvoiceCode();
                    newInvoiceCode = (String) result.getData();
                }

                for(TransactionLog transactionLog: transactionLogs){
                    Integer collectionId = transactionLog.getCollectionId();
                    Integer projectId = transactionLog.getProjectId();
                    //新增与 退费的项目所在同发票号的未退费项目 的缴费记录 --已缴费
                    if ( !returnedIdList.contains(transactionLog.getId()) &&
                            !transactionLog.getType().equals("挂号费")){
                        TransactionLog newTransactionLog = (TransactionLog) transactionLog.clone();
                        newTransactionLog.setId(null);
                        newTransactionLog.setRoleId(newCashierId);
                        newTransactionLog.setInvoiceCode(newInvoiceCode);
                        newTransactionLog.setStatus((byte)2);
                        CommonResult insertNewResult = transactionService.insertTransactionLog(newTransactionLog);
                        if (insertNewResult.getCode() == 500)
                            return insertNewResult;
                    }
                    //如果 退费的项目是药品，存在退部分数量的情况
                    else if(returnedIdList.contains(transactionLog.getId()) &&
                            transactionLog.getCollectionId().toString().startsWith("5")){
                        for(TransactionParam returnedProject: returnedProjectList){
                            if (returnedProject.getCollectionId().equals(collectionId) &&
                                    returnedProject.getProjectId().equals(projectId)){
                                Short returnAmount = returnedProject.getReturnAmount();
                                Short remainAmount = returnedProject.getRemainAmount();
                                BigDecimal newTotalFee = null;
                                if(returnedProject.getProjectStatus() == 5){
                                    newTotalFee = new BigDecimal(remainAmount).multiply(
                                            medicineMapper.getUnitPriceById(projectId));
                                }
                                else if(returnedProject.getProjectStatus() == 2){
                                    remainAmount = (short)(returnedProject.getRemainAmount() - returnAmount);
                                    if(remainAmount != 0) {
                                        newTotalFee = new BigDecimal(remainAmount).multiply(
                                                medicineMapper.getUnitPriceById(projectId));
                                    }
                                    else
                                       continue;
                                }

                                TransactionLog newTransactionLog = (TransactionLog) transactionLog.clone();
                                newTransactionLog.setId(null);
                                newTransactionLog.setRoleId(newCashierId);
                                newTransactionLog.setInvoiceCode(newInvoiceCode);
                                newTransactionLog.setAmount(remainAmount);
                                newTransactionLog.setStatus((byte)2);
                                newTransactionLog.setTotalMoney(newTotalFee);
                                CommonResult insertNewResult = transactionService.insertTransactionLog(newTransactionLog);
                                if (insertNewResult.getCode() == 500)
                                    return insertNewResult;
                            }
                        }
                    }
                }
                invoiceService.updateStatus((byte)3, newInvoiceCode);
            }

            //向异常表中添加新的记录
            CommonResult insertExceptionResult = transactionService.insertTransactionExceptionLog(
                    invoiceCode, newInvoiceCode, reverseInvoiceCode, newCashierId, "项目退费" );
            if (insertExceptionResult.getCode() == 500)
                return insertExceptionResult;
        }
        return CommonResult.success(transactionLogParams.size());
    }
    @Override
    public CommonResult reprintTransactionLog(String invoiceCode, Integer newCashierId){
        List<TransactionLog> transactionLogs = transactionLogMapper.listLogsByInvoiceCode(invoiceCode);
        String newInvoiceCode;
        synchronized (this) {
            //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
            CommonResult result = invoiceService.getNextInvoiceCode();
            newInvoiceCode = (String) result.getData();
        }

        int count = 0;
        for(TransactionLog transactionLog: transactionLogs){
            //更改原有的发票号对应的缴费状态
            transactionLog.setStatus((byte)5);
            count += transactionLogMapper.update(transactionLog);
            //插入新的缴费记录
            TransactionLog newTransactionLog = (TransactionLog) transactionLog.clone();
            newTransactionLog.setId(null);
            newTransactionLog.setRoleId(newCashierId);
            newTransactionLog.setInvoiceCode(newInvoiceCode);
            newTransactionLog.setStatus((byte) 2);
            CommonResult insertReverseResult = transactionService.insertTransactionLog(newTransactionLog);
            if (insertReverseResult.getCode() == 500)
                return insertReverseResult;
        }
        invoiceService.updateStatus((byte)3, newInvoiceCode);
        
        //向异常表中添加新的记录
        CommonResult insertExceptionResult = transactionService.insertTransactionExceptionLog(
                invoiceCode, newInvoiceCode, null, newCashierId, "发票重打" );
        if (insertExceptionResult.getCode() == 500)
            return insertExceptionResult;
        if(count == transactionLogs.size())
            return CommonResult.success(newInvoiceCode);
        else
            return CommonResult.fail();
    }

    @Override
    public CommonResult listLogsByRegistrationIdAndDate(Integer registrationId, String beginDateStr, String endDateStr){
        List<TransactionLog> transactionLogs = transactionLogMapper.listLogsByRegistrationIdAndDate(registrationId, beginDateStr, endDateStr);
        /*Map<String, List<TransactionLog>> invoiceMap = new HashMap<>();
        for(TransactionLog t: transactionLogs){
            String invoiceCode = t.getInvoiceCode();
           if(!invoiceMap.containsKey(invoiceCode)){
               invoiceMap.put(invoiceCode, new ArrayList<>());
               invoiceMap.get(invoiceCode).add(t);
           }
           else{
               invoiceMap.get(invoiceCode).add(t);
           }
        }*/
        return CommonResult.success(transactionLogs);
    }
}
