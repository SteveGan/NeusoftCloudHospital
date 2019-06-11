package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.alibaba.fastjson.JSONObject;
import com.neuedu.hospitalbackend.model.dao.MedicineMapper;
import com.neuedu.hospitalbackend.model.po.Medicine;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.MedicineManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static com.neuedu.hospitalbackend.util.ResultCode.E_602;

@Service
public class MedicineManagementImpl implements MedicineManagementService {
    @Resource
    MedicineMapper medicineMapper;

    @Override
    public CommonResult getMedicineById(Short id) {
        Medicine medicine = medicineMapper.get(id);
        return CommonResult.success(medicine);
    }

    @Override
    public CommonResult insertMedicine(Medicine medicine) {
        int count = medicineMapper.insert(medicine);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult updateMedicineById(Medicine medicine) {
        if (medicineMapper.get(medicine.getId()) == null) {
            return CommonResult.fail(E_602);
        }
        int count = medicineMapper.update(medicine);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult deleteMedicineById(Short id) {
        if (medicineMapper.get(id) == null) {
            return CommonResult.fail(E_602);
        }
        int count = medicineMapper.delete(id);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult listTraditionalMedicine(){
        List<Medicine> medicineList = medicineMapper.listTraditionalMedicine();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("medicines", medicineList);
        return CommonResult.success(medicineList);
    }

    @Override
    public CommonResult listModernMedicine(){
        List<Medicine> medicineList = medicineMapper.listModernMedicine();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("medicines", medicineList);
        return CommonResult.success(medicineList);
    }
}
