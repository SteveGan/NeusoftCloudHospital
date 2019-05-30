package com.neuedu.hospitalbackend.service.serviceinterface.pharmacystationservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.poback.Recipe;

import java.util.List;

public interface PharmacyService {

    /**
     * 根据患者的病历号，查询相应的已缴费尚未发放的药品信息
     * 两张表：transaction_log, recipe
     * @param object 患者的病历号, T.collection_id, R.id, T.status, R.status
     * @return 已缴费尚未发放的药品列表
     */
    List<Recipe> listPaidUndeliveredMedicine(JSONObject object);

    /**
     * 根据患者的病历号，查询开立的药品信息
     * @param recipe
     * @return
     */
    List<Recipe> listPaidMedicine(Recipe recipe);

    /**
     * 更新对应的recipe记录
     * @param recipe
     */
    void updateRecipe(Recipe recipe);

    /**
     * 更新对应药品的库存
     * @param return_amount 退还药品的数量
     * @param medicineCode 药品编码
     */
    void updateInventory(int return_amount, String medicineCode);
}
