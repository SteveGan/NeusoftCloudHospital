package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.MedicineMapper;
import com.neuedu.hospitalbackend.model.dao.PatientCaseMapper;
import com.neuedu.hospitalbackend.model.dao.RecipeMapper;
import com.neuedu.hospitalbackend.model.po.Recipe;
import com.neuedu.hospitalbackend.model.po.TransactionLog;
import com.neuedu.hospitalbackend.model.vo.*;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.InvoiceService;
import com.neuedu.hospitalbackend.service.serviceinterface.commonservice.TransactionService;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.RecipeManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import com.neuedu.hospitalbackend.util.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeManagementServiceImpl implements RecipeManagementService {

    @Resource
    private RecipeMapper recipeMapper;
    @Resource
    private MedicineMapper medicineMapper;
    @Resource
    private PatientCaseMapper patientCaseMapper;

    @Resource
    private InvoiceService invoiceService;
    @Resource
    private TransactionService transactionService;



    @Override
    public CommonResult listCurrentCaseRecipes(Integer caseId) {
        JSONObject returnJson = new JSONObject();

        List<HashMap> recipeLogs = recipeMapper.listRecipeInfoByRegistrationId(caseId);
        //处方类型：0该患者无处方，1中草药处方，2其他处方（成药）
        if(recipeLogs.size() == 0) {
            returnJson.put("type", 0);
            returnJson.put("recipes", null);
            return CommonResult.success(returnJson);
        }
        if((Integer)recipeLogs.get(0).get("medicineType") == 1)
            returnJson.put("type", 1);//中草药
        else
            returnJson.put("type", 2);//成药

        //转换格式
        HashMap<Integer, List<HashMap>> recipes = new HashMap<>();
        for(HashMap recipeLog : recipeLogs){
            Long recipeIdLong = (Long)recipeLog.get("id");
            Integer recipeId = new Integer(String.valueOf(recipeIdLong));
            List<HashMap> medicines;
            if(!recipes.containsKey(recipeId))
                medicines = new ArrayList<>();
            else
                medicines = recipes.get(recipeId);
            medicines.add(medicineInfo(recipeLog));
            recipes.put(recipeId, medicines);
        }
        JSONArray jsonArray = new JSONArray();
        for(Map.Entry<Integer, List<HashMap>> entry: recipes.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("recipeId", entry.getKey());
            jsonObject.put("medicines", entry.getValue());
            jsonArray.add(jsonObject);
        }
        returnJson.put("recipes", jsonArray);
        return CommonResult.success(returnJson);
    }

    /**
     * 格式转换
     * @param recipeLog
     * @return
     */
    public HashMap medicineInfo(HashMap recipeLog){
        HashMap medicine = new HashMap();
        medicine.put("medicineId", recipeLog.get("medicineId"));
        medicine.put("medicineName", recipeLog.get("medicineName"));
        medicine.put("amount", recipeLog.get("amount"));
        medicine.put("dosage", recipeLog.get("dosage"));
        medicine.put("status", recipeLog.get("status"));
        medicine.put("frequency", recipeLog.get("frequency"));
        medicine.put("medicineUnit", recipeLog.get("medicineUnit"));
        medicine.put("medicineType", recipeLog.get("medicineType"));
        medicine.put("medicineFormulation", recipeLog.get("medicineFormulation"));
        medicine.put("medicineSpecification", recipeLog.get("medicineSpecification"));
        return medicine;
    }

    /**
     * 新增处方
     * @return 处方id
     */
    @Override
    public CommonResult insertNewRecipe() {
        //设定申请清单id
        Integer recipeId = recipeMapper.getLatestId();
        if (recipeId == null)
            return CommonResult.fail(ResultCode.E_800);
        recipeId = recipeId + 1;
        //插入数据库
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.setMedicineId(0);//主键不能为空
        int count = recipeMapper.insertSelective(recipe);
        if (count <= 0)
            return CommonResult.fail();
        //返回处方编号
        JSONObject returnJson = new JSONObject();
        returnJson.put("recipeId", recipeId);
        return CommonResult.success(returnJson);
    }

    /**
     * 暂存处方
     * @param recipeCollectionParam
     * @return
     */
    public CommonResult preserveRecipes(RecipeCollectionParam recipeCollectionParam){
        return updateRecipes(recipeCollectionParam, 1);
    }

    /**
     * 开立处方
     * @param recipeCollectionParam
     * @return
     */
    public CommonResult submitRecipes(RecipeCollectionParam recipeCollectionParam){
        return updateRecipes(recipeCollectionParam, 2);
    }

    /**
     * 暂存/开立 更新数据
     * @param recipeCollectionParam
     * @param status
     * @return
     */
    public CommonResult updateRecipes(RecipeCollectionParam recipeCollectionParam, Integer status){
        int count = 0;
        Integer recipeId = recipeCollectionParam.getRecipeId();
        Integer caseId = recipeCollectionParam.getCaseId();
        Integer creatorRoleId = recipeCollectionParam.getCreatorRoleId();
        List<RecipeParam> medicines = recipeCollectionParam.getMedicines();

        //删除数据库已存在内容
        recipeMapper.deleteById(recipeId);

        //清屏删除
        if(medicines.size() == 0){
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);
            recipe.setMedicineId(0);
            recipe.setCaseId(caseId);
            recipe.setCreatorRoleId(creatorRoleId);
            count = recipeMapper.insertSelective(recipe);//保存recipeId
            if(count <= 0)
                return CommonResult.fail(ResultCode.E_802);
            return CommonResult.success(count);
        }

        //插入新提交内容
        for(RecipeParam recipeParam: medicines){
            //创建对象
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);
            recipe.setMedicineId(recipeParam.getMedicineId());
            recipe.setCaseId(caseId);
            recipe.setAmount(recipeParam.getAmount());
            recipe.setRemainAmount(recipeParam.getAmount());
            recipe.setMedicineSpecification(recipeParam.getMedicineSpecification());
            recipe.setMedicineUnit(recipeParam.getMedicineUnit());
            recipe.setMedicineFormulation(recipeParam.getMedicineFormulation());
            recipe.setDosage(recipeParam.getDosage());
            recipe.setFrequency(recipeParam.getFrequency());
            recipe.setCreatorRoleId(creatorRoleId);
            recipe.setStatus(recipeParam.getStatus());
            recipe.setMedicineType(recipeParam.getMedicineType());
            //另行查找并赋值medicineUnitPrice
            HashMap hashMap = medicineMapper.getMedicineTypeAndUPrice(recipeParam.getMedicineId());
            recipe.setMedicineUnitPrice((BigDecimal) hashMap.get("unitPrice"));
            recipeParam.setMedicineUnitPrice((BigDecimal) hashMap.get("unitPrice"));
            //插入数据库
            count = recipeMapper.insertSelective(recipe);
            if(count <= 0)
                return CommonResult.fail(ResultCode.E_802);

            //若开立，创建缴费清单
            if (status == 2) {
                CommonResult commonResult = insertTransactionLog(recipeCollectionParam, recipeParam);
                if (commonResult.getCode() != 200)
                    return CommonResult.fail(ResultCode.E_802);//保存失败
            }
        }
        return CommonResult.success(count);
    }

    /**
     * 创建缴费清单
     */
    public CommonResult insertTransactionLog(RecipeCollectionParam collectionParam, RecipeParam recipeParam){
        String newInvoiceCode;//获取可用发票号
        synchronized (this) {
            //通过查询invoice表得到新的缴费记录的发票号并将其状态改为已用
            CommonResult result = invoiceService.getNextInvoiceCode();
            newInvoiceCode = (String) result.getData();
        }
        Integer registrationId = collectionParam.getCaseId();
        Integer patientId = patientCaseMapper.getPatientIdByCaseId(registrationId);
        if(patientId == null)
            return CommonResult.fail(ResultCode.E_800);
        Integer roleId = collectionParam.getCreatorRoleId();
        Integer collectionId = collectionParam.getRecipeId();
        Integer projectId = recipeParam.getMedicineId();
        Short amount = recipeParam.getAmount();
        if(collectionId == null || projectId == null || amount == null)
            return CommonResult.fail(ResultCode.E_801);
        //计算总金额
        BigDecimal price = recipeParam.getMedicineUnitPrice();
        BigDecimal totalMoney = new BigDecimal(amount).multiply(price);

        //创建transactionLog对象
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setInvoiceCode(newInvoiceCode);
        transactionLog.setRegistrationId(registrationId);
        transactionLog.setPatientId(patientId);
        transactionLog.setRoleId(roleId);
        if(recipeParam.getMedicineType() == 1)
            transactionLog.setType("中草药");
        else
            transactionLog.setType("成药");
        transactionLog.setCollectionId(collectionId);
        transactionLog.setProjectId(projectId);
        transactionLog.setAmount(amount);
        transactionLog.setTotalMoney(totalMoney);

        //增加缴费清单
        return transactionService.insertTransactionLog(transactionLog);
    }






}
