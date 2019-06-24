package com.neuedu.hospitalbackend.service.serviceimplementation.pharmacystationservice;

import com.neuedu.hospitalbackend.model.dao.InventoryMapper;
import com.neuedu.hospitalbackend.model.dao.RecipeMapper;
import com.neuedu.hospitalbackend.model.vo.RecipeParam;
import com.neuedu.hospitalbackend.service.serviceinterface.pharmacystationservice.PharmacyService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_705;
import static com.neuedu.hospitalbackend.util.ResultCode.E_706;
import static com.neuedu.hospitalbackend.util.ResultCode.E_707;

/**
 * @author Polaris
 */
@Service
public class PharmacyServiceImpl implements PharmacyService {

    @Resource
    private RecipeMapper recipeMapper;
    @Resource
    private InventoryMapper inventoryMapper;


    public CommonResult listRecipesByRegistrationIdOrDate(Integer caseId, String chargeDateStr){
        String chargeDateBeginStr = null;
        String chargeDateEndStr = null;
        if(chargeDateStr != null){
            chargeDateBeginStr = chargeDateStr.concat(" 00:00:00");
            chargeDateEndStr = chargeDateStr.concat(" 23:59:59");
        }
        List<HashMap> result = recipeMapper.listRecipesByRegistrationIdOrDate(caseId, chargeDateBeginStr, chargeDateEndStr);
        return CommonResult.success(result);
    }

    public CommonResult listIndividualRecipe(Integer registrationId){
        List<HashMap> recipe = recipeMapper.listIndividualRecipe(registrationId);
        return CommonResult.success(recipe);
    }

    public CommonResult deliverMedicine(List<RecipeParam> recipeParams){
        int count = 0;

        for(RecipeParam r: recipeParams){
            Byte transactionLogStatus = r.getTransactionLogStatus();
            Byte medicineStatus = r.getStatus();
            Integer medicineId = r.getMedicineId();
            Integer recipeId = r.getId();
            Integer deliverRoleId = r.getDeliverRoleId();
            Short remainAmount = r.getRemainAmount();

            //取药条件：已缴费 + 开立 且该要有库存
            if(transactionLogStatus == 2 && medicineStatus == 2){
                //查询药品库存
                if (inventoryMapper.getRemainingAmountByMedicineId(medicineId) > remainAmount){
                    //更新对应的recipe状态和发药人id --已取药
                    count += recipeMapper.updateStatusAndDeliverId(recipeId, medicineId, (byte) 4,deliverRoleId);
                    //更新对应药品的库存
                    inventoryMapper.updateInventory(medicineId, remainAmount);
                }
                else
                    return CommonResult.fail(E_707);
            }
            else
                return CommonResult.fail(E_706);
        }
        if(count == recipeParams.size())
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }

    public CommonResult returnMedicine(List<RecipeParam> recipeParams){
        int count = 0;
        for(RecipeParam r: recipeParams){
            Byte transactionLogStatus = r.getTransactionLogStatus();
            Byte medicineStatus = r.getStatus();
            Integer medicineId = r.getMedicineId();
            Integer recipeId = r.getId();
            Integer deliverRoleId = r.getDeliverRoleId();
            Short remainAmount = r.getRemainAmount();
            Short returnAmount = r.getReturnAmount();

            //退药条件：已缴费 + 已取药 / 已缴费 + 已退药且remainAmount > returnAmount
            if(transactionLogStatus == 2 && (medicineStatus == 4 || medicineStatus == 5)){
                if (returnAmount <= remainAmount){
                    //更新对应的药品剩余数量
                    count += recipeMapper.updateRemainAmount(recipeId, medicineId, returnAmount);
                    //更新对应的recipe状态和退药人
                    count += recipeMapper.updateStatusAndDeliverId(recipeId, medicineId, (byte)5, deliverRoleId);
                    //更新对应药品的库存
                    inventoryMapper.updateInventory(medicineId, (short) - returnAmount);
                }
                else
                    return CommonResult.fail(E_705);
            }
            else
                return CommonResult.fail(E_705);
        }
        if(count == 2 * recipeParams.size())
            return CommonResult.success(count);
        else
            return CommonResult.fail();
    }
}
