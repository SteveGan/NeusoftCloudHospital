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
            //取药条件：已缴费 + 开立 且该要有库存
            if(r.getTransactionLogStatus() == 2 && r.getStatus() == 2){
                //查询药品库存
                if (inventoryMapper.getRemainingAmountByMedicineId(r.getMedicineId()) > 0){
                    //更新对应的recipe状态和发药人id --已取药
                    count += recipeMapper.updateStatusAndDeliverId(r.getId(), r.getMedicineId(), (byte) 4, r.getDeliverRoleId());
                    //更新对应药品的库存
                    inventoryMapper.updateInventory(r.getMedicineId(), r.getRemainAmount());
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
            //退药条件：已缴费 + 已取药 / 已缴费 + 已退药且remainAmount > returnAmount
            if(r.getTransactionLogStatus() == 2 && (r.getStatus() == 4 || r.getStatus() == 5)){
                if (r.getReturnAmount() <= r.getRemainAmount()){
                    //更新对应的药品剩余数量
                    count += recipeMapper.updateRemainAmount(r.getId(), r.getMedicineId(), r.getReturnAmount());
                    //更新对应的recipe状态和退药人
                    count += recipeMapper.updateStatusAndDeliverId(r.getId(), r.getMedicineId(), (byte)5, r.getDeliverRoleId());
                    //更新对应药品的库存
                    inventoryMapper.updateInventory(r.getMedicineId(), (short) - r.getReturnAmount());
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
