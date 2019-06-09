package com.neuedu.hospitalbackend.service.serviceimplementation.tollstationservice;

import com.alibaba.fastjson.JSONArray;
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
    private InvoiceService invoiceService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public CommonResult listTransactionLogs(Integer registrationId) {
        List<HashMap> invoiceCollection = transactionLogMapper.getInvoiceCodeAndStatusByRegistrationId(registrationId);
        Map<String, List<HashMap>> result = new HashMap<>();
        List<HashMap> logs = new ArrayList<>();
        logs.add(transactionLogMapper.getRegistrationLog(registrationId));
        logs.addAll(transactionLogMapper.listLogsByTableName("inspection", registrationId));
        logs.addAll(transactionLogMapper.listLogsByTableName("examination", registrationId));
        logs.addAll(transactionLogMapper.listLogsByTableName("treatment", registrationId));
        logs.addAll(transactionLogMapper.getRecipeLogs(registrationId));
        for(HashMap log: logs){
            System.out.println("log" + log);
            for(HashMap t: invoiceCollection){
                if(log.get("invoiceCode").equals(t.get("invoiceCode"))){
                    if(result.containsKey(t.get("invoiceCode")))
                        result.get(t.get("invoiceCode")).add(log);
                    else{
                        result.put((String) t.get("invoiceCode"), new ArrayList<>());
                        result.get(t.get("invoiceCode")).add(log);
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
    public CommonResult updateTransactionLogsAsPaid(List<TransactionLog> transactionLogs) {
        int count = 0;
        for (TransactionLog transactionLog : transactionLogs) {
            if (transactionLog.getStatus() == 1) {
                transactionLog.setStatus((byte) 2);
                count += transactionLogMapper.update(transactionLog);
            }
        }
        if (count != transactionLogs.size())
            return CommonResult.fail(E_700);
        return CommonResult.success(count);
    }

    @Override
    public CommonResult updateTransactionLogsAsReturned(List<TransactionParam> transactionLogParams) {
        //将前端传进来的所有待退费记录转换成<collectionId, List<TransactionParam>>的形式
        HashMap<Integer, List<TransactionParam>> collection = new HashMap<>();
        for (TransactionParam t : transactionLogParams) {
            Integer collectionId = t.getCollectionId();
            if (!collection.containsKey(collectionId)) {
                List<TransactionParam> projects = new ArrayList<>();
                projects.add(t);
                collection.put(collectionId, projects);
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
            // 得到集合的类别（检验、检查、处置、药方）
            Byte itemCategory = returnedProjectList.get(0).getItemCategory();
            //得到退费项目所在集合的发票号
            String originalInvoiceCode = returnedProjectList.get(0).getInvoiceCode();

            // 先判断collection中的项目是否都为可退状态，如果有一项不可退，则返回给前端，提示重新选择
            for(TransactionParam project: returnedProjectList){
                if (project.getStatus() == 2){
                    if ( !((project.getItemCategory() != 4 && project.getItemStatus() == 2)
                            || (project.getItemCategory() == 4 && project.getItemStatus() == 5)
                            || (project.getItemCategory() == 4 && project.getItemStatus() == 2))){
                        CommonResult.fail(E_704);
                    }
                }
                else
                    CommonResult.fail(E_704);
            }

            // 在检查/检验/处置表中更改想要退费的项目状态 （即检验单B项目1）--已作废
            for(TransactionParam project: returnedProjectList){
                switch (itemCategory){
                    case 1:
                        inspectionMapper.updateStatus(project.getCollectionId(), project.getProjectId(), (byte) 3);
                        break;
                    case 2:
                        examinationMapper.updateStatus(project.getCollectionId(), project.getProjectId(), (byte) 3);
                        break;
                    case 3:
                        treatmentMapper.updateStatus(project.getCollectionId(), project.getProjectId(), (byte) 3);
                        break;
                    case 4:
                        //如果药品本身是已退药的状态,则无需更改对应药品状态
                        //如果药品本身是开立（未取药）的状态, 则需更改对应处方中药品的return amount
                        recipeMapper.updateAmount(project.getCollectionId(), project.getProjectId(), project.getReturnAmount());
                        break;
                }
            }

            // 得到 退费的项目所在第一层级的(同一个发票号、同一个collectionId） 缴费记录
            List<TransactionLog> transactionLogs = transactionLogMapper.listCollectionProjectInfo(collectionId, originalInvoiceCode);
            // 更改 退费的项目所在第一层级的 相关缴费记录状态（即检验单B中的所有项目）--已退费
            transactionLogMapper.updateSelectiveAsReturned(collectionId,originalInvoiceCode);

            String reverseInvoiceCode = null;
            String newInvoiceCode = null;
            //向缴费表中添加 退费的项目所在第一层级的 冲正记录（即检验单B中的所有项目） --冲正，
            synchronized (this) {
                //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
                CommonResult result = invoiceService.getNextInvoiceCode();
                reverseInvoiceCode = (String) result.getData();
            }

            for(TransactionLog transactionLog: transactionLogs){
                //向缴费表中添加新的缴费记录  --冲正
                TransactionLog reverseTransactionLog = transactionLog;
                reverseTransactionLog.setId(null);
                reverseTransactionLog.setInvoiceCode(reverseInvoiceCode);
                reverseTransactionLog.setStatus((byte)4);
                reverseTransactionLog.setTotalMoney(transactionLog.getTotalMoney().negate());
                CommonResult insertReverseResult = transactionService.insertTransactionLog(reverseTransactionLog);
                if (insertReverseResult.getCode() == 500)
                    return insertReverseResult;
            }

            // 如果某个collection下的所有项目没有都被退费，则产生新的缴费记录
            if(transactionLogs.size() != returnedProjectList.size()) {
                synchronized (this) {
                    //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
                    CommonResult result = invoiceService.getNextInvoiceCode();
                    newInvoiceCode = (String) result.getData();
                }

                for(TransactionLog transactionLog: transactionLogs){
                    //新增与 退费的项目所在相同第一层级的未退费项目 的缴费记录（即检验单B中的项目2、3）--已缴费
                    if ( (!returnedProjectIdList.contains(transactionLog.getProjectId())) &&
                            (transactionLog.getType() != "挂号费")){
                        transactionLog.setId(null);
                        transactionLog.setInvoiceCode(newInvoiceCode);
                        transactionLog.setStatus((byte)2);
                        transactionLog.setTotalMoney(transactionLog.getTotalMoney().negate());
                        CommonResult insertNewResult = transactionService.insertTransactionLog(transactionLog);
                        if (insertNewResult.getCode() == 500)
                            return insertNewResult;
                    }
                    //如果 退费的项目是药品，存在退部分数量的情况
                    else if(returnedProjectIdList.contains(transactionLog.getProjectId()) &&
                            itemCategory== 4){
                        for(TransactionParam returnedProject: returnedProjectList){
                            if (returnedProject.getProjectId() == transactionLog.getProjectId()){
                                Short returnAmount = returnedProject.getReturnAmount();
                                if(returnAmount != returnedProject.getAmount()){
                                    BigDecimal newTotalFee = new BigDecimal(returnAmount).multiply(
                                            medicineMapper.getUnitPrizeById(returnedProject.getProjectId()));
                                    transactionLog.setId(null);
                                    transactionLog.setInvoiceCode(newInvoiceCode);
                                    transactionLog.setAmount((short) ((short)transactionLog.getAmount() - returnAmount));
                                    transactionLog.setStatus((byte)2);
                                    transactionLog.setTotalMoney(newTotalFee);
                                    CommonResult insertNewResult = transactionService.insertTransactionLog(transactionLog);
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
                    originalInvoiceCode, newInvoiceCode, reverseInvoiceCode, transactionLogs.get(0).getRoleId(), "项目退费" );
            if (insertExceptionResult.getCode() == 500)
                return insertExceptionResult;
        }
        return CommonResult.success(transactionLogParams.size());
    }

}
