package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.*;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.model.vo.TransactionParam;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.service.serviceinterface.tollstationservice.PaymentService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.neuedu.hospitalbackend.util.ResultCode.E_700;
import static com.neuedu.hospitalbackend.util.ResultCode.E_704;
import static com.neuedu.hospitalbackend.util.ResultCode.E_708;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionLogMapper transactionLogMapper;
    @Autowired
    private ExaminationMapper examinationMapper;
    @Autowired
    private InspectionMapper inspectionMapper;
    @Autowired
    private TreatmentMapper treatmentMapper;
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private MedicineMapper medicineMapper;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private TransactionService transactionService;

    @Override
    public CommonResult listDetailedTransactionLogs(Integer registrationId) {
        //列出指定用户的所有发票号、缴费状态（已缴费、已退费、冲正、作废、冻结）
        List<HashMap> invoiceCollection = transactionLogMapper.getInvoiceCodeAndStatusByRegistrationId(registrationId);
        Map<String, List<HashMap>> result = new HashMap<>();
        List<HashMap> logs = new ArrayList<>();
        //列出指定用户的待缴费、已缴费状态缴费记录  第一层目录 -- 第二层目录
        logs.add(transactionLogMapper.getRegistrationLog(registrationId));
        logs.addAll(transactionLogMapper.listLogsByTableName("inspection", registrationId));
        logs.addAll(transactionLogMapper.listLogsByTableName("examination", registrationId));
        logs.addAll(transactionLogMapper.listLogsByTableName("treatment", registrationId));
        logs.addAll(transactionLogMapper.getRecipeLogs(registrationId));
        for(HashMap log: logs){
            String logInvoiceCode = (String) log.get("invoiceCode");
            for(HashMap t: invoiceCollection){
                String invoiceCode = (String) t.get("invoice_code");
                if( logInvoiceCode.equals(invoiceCode) && log.get("status") == t.get("status")){
                    if(result.containsKey(invoiceCode))
                        result.get(invoiceCode).add(log);
                    else{
                        result.put(invoiceCode, new ArrayList<>());
                        result.get(invoiceCode).add(log);
                    }
                }
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("invoiceCollection", invoiceCollection);
        jsonObject.put("transactionLogs", result);
        return CommonResult.success(jsonObject);
    }

    @Override
    public CommonResult listCollectionDetailedItems(Integer collectionId, Integer projectId){
        // 判断集合的类别（检查、检验、处置、药方）
        List<HashMap> itemInfo;
        if(collectionId.toString().startsWith("2")) {
            itemInfo = transactionLogMapper.listItemsByCollectionIdAndProjectId(collectionId, projectId);
        } else if(collectionId.toString().startsWith("3")) {
            itemInfo = transactionLogMapper.listItemsByCollectionIdAndProjectId(collectionId, projectId);
        } else
            return CommonResult.success("不存在小项");
        return CommonResult.success(itemInfo);
    }

    @Override
    public CommonResult updateTransactionLogsAsPaid(List<TransactionLog> transactionLogs) {
        String invoiceCode;
        synchronized (this) {
            //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
            CommonResult result = invoiceService.getNextInvoiceCode();
            invoiceCode = (String) result.getData();
        }
        int count = 0;
        for (TransactionLog transactionLog : transactionLogs) {
            if (transactionLog.getStatus() == 1) {
                transactionLog.setStatus((byte) 2);
                //transactionLog.setInvoiceCode(invoiceCode);
                count += transactionLogMapper.update(transactionLog);
            }
        }
        if (count != transactionLogs.size())
            return CommonResult.fail(E_700);
        return CommonResult.success(count);
    }

    @Override
    public CommonResult updateTransactionLogsAsReturned(List<TransactionParam> transactionLogParams) {
        Integer newCashierId = null;
        //将前端传进来的所有待退费记录转换成<collectionId, List<TransactionParam>>的形式
        HashMap<Integer, List<TransactionParam>> collection = new HashMap<>();
        for (TransactionParam t : transactionLogParams) {
            Integer collectionId = t.getCollectionId();
            if (!collection.containsKey(collectionId)) {
                collection.put(collectionId, new ArrayList<>());
                collection.get(collectionId).add(t);
            } else
                collection.get(collectionId).add(t);
        }

        //执行退费系列操作
        for(Integer collectionId: collection.keySet()){
            // 检验单B中退费的项目集合
            List<TransactionParam> returnedProjectList = collection.get(collectionId);
            // 检验单B中退费的项目id集合
            List<Integer> returnedProjectIdList = new ArrayList<>();
            for(int i=0; i < returnedProjectList.size(); i++){
                returnedProjectIdList.add(returnedProjectList.get(i).getProjectId());
            }
            // 判断集合的类别（检查、检验、处置、药方）
            Byte itemCategory = null;
            if(collectionId.toString().startsWith("2")) {
                itemCategory = 1;
            } else if(collectionId.toString().startsWith("3")) {
                itemCategory = 2;
            } else if(collectionId.toString().startsWith("4")) {
                itemCategory = 3;
            } else if(collectionId.toString().startsWith("5")) {
                itemCategory = 4;
            }
            //得到退费项目所在集合的发票号
            String originalInvoiceCode = returnedProjectList.get(0).getInvoiceCode();

            // 先判断collection中的项目是否都为可退状态，如果有一项不可退，则返回给前端，提示重新选择
            //医技项目退费条件：已缴费 + 开立
            //药方退费条件：已缴费 + 已退药 / 已缴费 + 开立
            for(TransactionParam returnedProject: returnedProjectList){
                Byte returnedTransactionStatus = returnedProject.getStatus(); //缴费记录状态
                Byte returnedProjectStatus = returnedProject.getItemStatus(); //项目自身状态
                if (returnedTransactionStatus == 2){
                    if (!((itemCategory != 4 && returnedProjectStatus == 2)
                            || (itemCategory == 4 && returnedProjectStatus == 5)
                            || (itemCategory == 4 && returnedProjectStatus == 2))){
                       return CommonResult.fail(E_704);
                    }
                    else if(itemCategory == 4 && returnedProject.getRemainAmount() < returnedProject.getReturnAmount())
                        return CommonResult.fail(E_704);
                }
                else
                    return CommonResult.fail(E_704);
            }

            // 在检查/检验/处置表中更改想要退费的项目状态 （即检验单B项目1）--已作废
            for(TransactionParam returnedProject: returnedProjectList){
                newCashierId = returnedProject.getNewCashierId();
                Integer returnedCollectionId = returnedProject.getCollectionId();
                Integer returnedProjectId = returnedProject.getProjectId();
                Byte returnedItemStatus = returnedProject.getItemStatus();
                Short returnedReturnAmount = returnedProject.getReturnAmount();
                Short returnedRemainAmount = returnedProject.getRemainAmount();
                switch (itemCategory){
                    case 1:
                        inspectionMapper.updateStatus(returnedCollectionId, returnedProjectId, (byte) 3);
                        break;
                    case 2:
                        examinationMapper.updateStatus(returnedCollectionId, returnedProjectId, (byte) 3);
                        break;
                    case 3:
                        treatmentMapper.updateStatus(returnedCollectionId, returnedProjectId, (byte) 3);
                        break;
                    case 4:
                        //如果药品本身是已退药的状态,则无需更改对应药品状态和数量
                        //如果药品本身是开立（未取药）的状态，则需更改对应处方中药品的remain amount
                        if(returnedItemStatus == 2){
                            recipeMapper.updateRemainAmount(returnedCollectionId, returnedProjectId, returnedReturnAmount);
                            //如果退药数量 = 药品可退数量，则该药品状态应改为作废
                            if(returnedReturnAmount.equals(returnedRemainAmount))
                                recipeMapper.updateStatus(returnedCollectionId, returnedProjectId, (byte) 3);
                        }
                        break;
                }
            }

            // 得到 退费的项目所在第一层级的(同一个发票号、同一个collectionId） 缴费记录
            List<TransactionLog> transactionLogs = transactionLogMapper.listCollectionProjectInfo(collectionId, originalInvoiceCode);

            // 更改 退费的项目所在第一层级的 相关缴费记录状态（即检验单B中的所有项目）--已退费
            transactionLogMapper.updateSelectiveAsReturned(collectionId,originalInvoiceCode);

            String reverseInvoiceCode;
            String newInvoiceCode = null;

            //向缴费表中添加 退费的项目所在第一层级的 冲正记录（即检验单B中的所有项目） --冲正，
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

            // 如果某个collection下的所有项目没有都被退费，则产生新的缴费记录
            // 对于医技项目来说，没有都被退费： transactionLogs.size() != returnedProjectList.size()
            //对于药品来说，没有都被退费：某个退费的项目returnAmount != amount / transactionLogs.size() != returnedProjectList.size()
            boolean isRecipeAllReturn = true;
            if(itemCategory == 4){
                for(TransactionParam project: returnedProjectList){
                    if( !project.getRemainAmount().equals(project.getReturnAmount()))
                        isRecipeAllReturn = false;
                }
            }
            if(transactionLogs.size() != returnedProjectList.size() || !isRecipeAllReturn) {
                synchronized (this) {
                    //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
                    CommonResult result = invoiceService.getNextInvoiceCode();
                    newInvoiceCode = (String) result.getData();
                }

                for(TransactionLog transactionLog: transactionLogs){
                    if(transactionLog.getCollectionId() == collectionId)
                    //新增与 退费的项目所在相同第一层级的未退费项目 的缴费记录（即检验单B中的项目2、3）--已缴费
                    if ( (!returnedProjectIdList.contains(transactionLog.getProjectId())) &&
                            (!transactionLog.getType().equals("挂号费")) ){
                        TransactionLog newTransactionLog = (TransactionLog) transactionLog.clone();
                        newTransactionLog.setId(null);
                        newTransactionLog.setRoleId(newCashierId);
                        newTransactionLog.setInvoiceCode(newInvoiceCode);
                        newTransactionLog.setStatus(transactionLog.getStatus());
                        CommonResult insertNewResult = transactionService.insertTransactionLog(newTransactionLog);
                        if (insertNewResult.getCode() == 500)
                            return insertNewResult;
                    }
                    //如果 退费的项目是药品，存在退部分数量的情况
                    else if(returnedProjectIdList.contains(transactionLog.getProjectId()) && itemCategory== 4){
                        for(TransactionParam returnedProject: returnedProjectList){
                            if (returnedProject.getProjectId().equals(transactionLog.getProjectId())){
                                Short returnAmount = returnedProject.getReturnAmount();
                                Short remainAmount = (short) (returnedProject.getRemainAmount() - returnAmount);
                                if(remainAmount != 0){
                                    BigDecimal newTotalFee = new BigDecimal(remainAmount).multiply(
                                            medicineMapper.getUnitPriceById(returnedProject.getProjectId()));
                                    TransactionLog newTransactionLog = (TransactionLog) transactionLog.clone();
                                    newTransactionLog.setId(null);
                                    newTransactionLog.setRoleId(newCashierId);
                                    newTransactionLog.setInvoiceCode(newInvoiceCode);
                                    newTransactionLog.setAmount(remainAmount);
                                    newTransactionLog.setStatus(transactionLog.getStatus());
                                    newTransactionLog.setTotalMoney(newTotalFee);
                                    CommonResult insertNewResult = transactionService.insertTransactionLog(newTransactionLog);
                                    if (insertNewResult.getCode() == 500)
                                        return insertNewResult;
                                }
                            }
                        }
                    }
                }
            }

            //向异常表中添加新的记录
            CommonResult insertExceptionResult = transactionService.insertTransactionExceptionLog(
                    originalInvoiceCode, newInvoiceCode, reverseInvoiceCode, newCashierId, "项目退费" );
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
            transactionLog.setStatus((byte) 5);
            count += transactionLogMapper.update(transactionLog);
            //插入新的缴费记录
            transactionLog.setId(null);
            transactionLog.setRoleId(newCashierId);
            transactionLog.setInvoiceCode(newInvoiceCode);
            transactionLog.setStatus((byte) 2);
            CommonResult insertReverseResult = transactionService.insertTransactionLog(transactionLog);
            if (insertReverseResult.getCode() == 500)
                return insertReverseResult;

        }
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
        Map<String, List<TransactionLog>> invoiceMap = new HashMap<>();
        for(TransactionLog t: transactionLogs){
            String invoiceCode = t.getInvoiceCode();
           if(!invoiceMap.containsKey(invoiceCode)){
               invoiceMap.put(invoiceCode, new ArrayList<>());
               invoiceMap.get(invoiceCode).add(t);
           }
           else{
               invoiceMap.get(invoiceCode).add(t);
           }
        }
        return CommonResult.success(invoiceMap);
    }
}
