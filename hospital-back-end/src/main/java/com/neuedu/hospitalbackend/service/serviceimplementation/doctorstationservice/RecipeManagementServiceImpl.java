package com.neuedu.hospitalbackend.service.serviceimplementation.doctorstationservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.RecipeMapper;
import com.neuedu.hospitalbackend.service.serviceinterface.doctorstationservice.RecipeManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeManagementServiceImpl implements RecipeManagementService {

    @Resource
    private RecipeMapper recipeMapper;

    @Override
    public CommonResult listCurrentCaseRecipes(Integer caseId) {
        JSONObject returnJson = new JSONObject();

        List<HashMap> recipeLogs = recipeMapper.listRecipeInfoByRegistrationId(caseId);
        //处方类型：0该患者无处方，1中草药处方，2其他处方（成药）
        if(recipeLogs == null)
            returnJson.put("type", 0);
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
        medicine.put("frequency", recipeLog.get("frequency"));
        medicine.put("medicineUnit", recipeLog.get("medicineUnit"));
        medicine.put("medicineType", recipeLog.get("medicineType"));
        medicine.put("medicineFormulation", recipeLog.get("medicineFormulation"));
        medicine.put("medicineSpecification", recipeLog.get("medicineSpecification"));
        return medicine;
    }


}
