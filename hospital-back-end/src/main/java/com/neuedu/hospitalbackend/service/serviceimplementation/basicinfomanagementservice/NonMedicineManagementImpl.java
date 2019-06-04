package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;

import com.neuedu.hospitalbackend.model.dao.NonMedicineMapper;
import com.neuedu.hospitalbackend.model.po.NonMedicine;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.NonMedicineManagementService;
import com.neuedu.hospitalbackend.util.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.neuedu.hospitalbackend.util.ResultCode.E_602;

@Service
public class NonMedicineManagementImpl implements NonMedicineManagementService {
    @Resource
    NonMedicineMapper nonMedicineMapper;

    @Override
    public CommonResult getNonMedicineById(Integer id) {
        NonMedicine nonMedicine = nonMedicineMapper.get(id);
        return CommonResult.success(nonMedicine);
    }

    @Override
    public CommonResult insertNonMedicine(NonMedicine disease) {
        int count = nonMedicineMapper.insert(disease);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult updateNonMedicineById(NonMedicine nonMedicine) {
        if (nonMedicineMapper.get(nonMedicine.getId()) == null) {
            return CommonResult.fail(E_602);
        }
        int count = nonMedicineMapper.update(nonMedicine);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }

    @Override
    public CommonResult deleteNonMedicineById(Integer id) {
        if (nonMedicineMapper.get(id) == null) {
            return CommonResult.fail(E_602);
        }
        int count = nonMedicineMapper.delete(id);
        if (count == 0) {
            return CommonResult.fail();
        }
        return CommonResult.success(count);
    }
}
